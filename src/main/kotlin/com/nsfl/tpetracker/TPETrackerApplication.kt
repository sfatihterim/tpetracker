package com.nsfl.tpetracker

import org.jsoup.Jsoup
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.regex.Pattern

private const val PLAYERS_HTML = "<!DOCTYPE html><html><head><title>%s</title><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=[%s];\$(document).ready(function(){\$('#example').DataTable({paging:false,order: [[4, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Team' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding-left: 5%%; padding-right: 5%%; padding-top: 0.5%%; padding-bottom: 0.5%%;}</style></head><body><div><table id=\"example\" class=\"ui celled table\" width=\"100%%\"></table></div></body></html>"
private const val TEAM_HTML = "<!DOCTYPE html><html><head><title>%s</title><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=[%s];\$(document).ready(function(){\$('#example').DataTable({paging:false,order: [[3, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding-left: 5%%; padding-right: 5%%; padding-top: 0.5%%; padding-bottom: 0.5%%;}caption{padding-bottom: 30px; font-size: 30px}</style></head><body><div><table id=\"example\" class=\"ui celled table\" width=\"100%%\"><caption>%s</caption></table></div></body></html>"

@RestController
@SpringBootApplication
@EnableScheduling
class TpeTrackerApplication {

    private var baltimoreHawksRoster = ArrayList<Player>()
    private var coloradoYetiRoster = ArrayList<Player>()
    private var philadelphiaLibertyRoster = ArrayList<Player>()
    private var yellowknifeWraithsRoster = ArrayList<Player>()
    private var arizonaOutlawsRoster = ArrayList<Player>()
    private var newOrleansSecondLineRoster = ArrayList<Player>()
    private var orangeCountyOttersRoster = ArrayList<Player>()
    private var sanJoseSabercatsRoster = ArrayList<Player>()
    private var palmBeachSolarBearsRoster = ArrayList<Player>()
    private var kansasCityCoyotesRoster = ArrayList<Player>()
    private var portlandPythonsRoster = ArrayList<Player>()
    private var norfolkSeawolvesRoster = ArrayList<Player>()
    private var sanAntonioMarshalsRoster = ArrayList<Player>()
    private var tijuanaLuchadoresRoster = ArrayList<Player>()

    @Scheduled(fixedRate = 6 * 60 * 60000)
    fun refreshData() {
        baltimoreHawksRoster = parsePlayers(Team.BALTIMORE_HAWKS)
        coloradoYetiRoster = parsePlayers(Team.COLORADO_YETI)
        philadelphiaLibertyRoster = parsePlayers(Team.PHILADELPHIA_LIBERTY)
        yellowknifeWraithsRoster = parsePlayers(Team.YELLOWKNIFE_WRAITHS)
        arizonaOutlawsRoster = parsePlayers(Team.ARIZONA_OUTLAWS)
        newOrleansSecondLineRoster = parsePlayers(Team.NEW_ORLEANS_SECOND_LINE)
        orangeCountyOttersRoster = parsePlayers(Team.ORANGE_COUNTY_OTTERS)
        sanJoseSabercatsRoster = parsePlayers(Team.SAN_JOSE_SABERCATS)
        palmBeachSolarBearsRoster = parsePlayers(Team.PALM_BEACH_SOLAR_BEARS)
        kansasCityCoyotesRoster = parsePlayers(Team.KANSAS_CITY_COYOTES)
        portlandPythonsRoster = parsePlayers(Team.PORTLAND_PYTHONS)
        norfolkSeawolvesRoster = parsePlayers(Team.NORFOLK_SEAWOLVES)
        sanAntonioMarshalsRoster = parsePlayers(Team.SAN_ANTONIO_MARSHALS)
        tijuanaLuchadoresRoster = parsePlayers(Team.TIJUANA_LUCHADORES)
    }

    private fun parsePlayers(team: Team): ArrayList<Player> {

        val playerList = ArrayList<Player>()

        listOf(
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=${team.id}").get(),
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=${team.id}&st=15").get(),
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=${team.id}&st=30").get()
        ).forEach { document ->
            document.body().getElementById("topic-list").allElements
                    .filter { element ->
                        element.toString().let {
                            it.startsWith("<td class=\"row4\"")
                                    && it.contains(">(S")
                        }
                    }.forEach { element ->
                        element.toString().let {
                            val playerInfo = parsePlayerInfo(it).split("?")
                            if (playerInfo.size == 3) {
                                playerList.add(
                                        Player(
                                                playerInfo[1].trim(),
                                                team,
                                                Position.fromAbbreviation(playerInfo[2].trim()),
                                                playerInfo[0].trim().let { info ->
                                                    if (info.length == 2) {
                                                        "S0${info.substring(1)}"
                                                    } else {
                                                        info
                                                    }
                                                },
                                                parseTPE(it),
                                                parseUrl(it)
                                        )
                                )
                            }
                        }
                    }
        }

        return playerList
    }

    private fun parsePlayerInfo(elementString: String): String {

        val startIndex = elementString.indexOf(">(S") + 1
        val endIndex = elementString.indexOf("<", startIndex)

        return elementString.substring(startIndex, endIndex)
                .replace("–", "-")
                .replace("(", "")
                .replace(")", "")
                .replace("'", "’")
                .replaceFirst("-", "?")
                .reversed()
                .replaceFirst("-", "?")
                .reversed()
    }

    private fun parseTPE(elementString: String): Int {
        return try {
            val startIndex = elementString.indexOf("TPE:")
            val endIndex = elementString.indexOf("<", startIndex)

            elementString.substring(startIndex, endIndex)
                    .replace(Pattern.compile("[^0-9.]").toRegex(), "")
                    .toInt()
        } catch (exception: Exception) {
            50
        }
    }

    private fun parseUrl(elementString: String): String {
        val startIndex = elementString.indexOf("href=") + 6
        val endIndex = elementString.indexOf("\"", startIndex)
        return elementString.substring(startIndex, endIndex)
    }

    @RequestMapping
    fun getIndex() =
            "<title>TPE Tracker Index</title>" +
                    "<a href=\"/all_players\">All Players</a><br><br>" +
                    "<a href=\"/baltimore_hawks\">Baltimore Hawks</a><br><br>" +
                    "<a href=\"/colorado_yeti\">Colorado Yeti</a><br><br>" +
                    "<a href=\"/philadelphia_liberty\">Philadelphia Liberty</a><br><br>" +
                    "<a href=\"/yellowknife_wraiths\">Yellowknife Wraiths</a><br><br>" +
                    "<a href=\"/arizona_outlaws\">Arizona Outlaws</a><br><br>" +
                    "<a href=\"/new_orleans_second_line\">New Orleans Second Line</a><br><br>" +
                    "<a href=\"/orange_county_otters\">Orange County Otters</a><br><br>" +
                    "<a href=\"/san_jose_sabercats\">San Jose SaberCats</a><br><br>" +
                    "<a href=\"/palm_beach_solar_bears\">Palm Beach Solar Bears</a><br><br>" +
                    "<a href=\"/kansas_city_coyotes\">Kansas City Coyotes</a><br><br>" +
                    "<a href=\"/portland_pythons\">Portland Pythons</a><br><br>" +
                    "<a href=\"/norfolk_seawolves\">Norfolk SeaWolves</a><br><br>" +
                    "<a href=\"/san_antonio_marshals\">San Antonio Marshals</a><br><br>" +
                    "<a href=\"/tijuana_luchadores\">Tijuana Luchadores</a><br><br>"

    @RequestMapping("/all_players")
    fun getAllPlayers() = createPlayersHTML(
            baltimoreHawksRoster,
            coloradoYetiRoster,
            philadelphiaLibertyRoster,
            yellowknifeWraithsRoster,
            arizonaOutlawsRoster,
            newOrleansSecondLineRoster,
            orangeCountyOttersRoster,
            sanJoseSabercatsRoster,
            palmBeachSolarBearsRoster,
            kansasCityCoyotesRoster,
            portlandPythonsRoster,
            norfolkSeawolvesRoster,
            sanAntonioMarshalsRoster,
            tijuanaLuchadoresRoster
    )

    @RequestMapping("/baltimore_hawks")
    fun getBaltimoreHawksRoster() = createTeamHTML(
            Team.BALTIMORE_HAWKS,
            baltimoreHawksRoster
    )

    @RequestMapping("/colorado_yeti")
    fun getColoradoYetiRoster() = createTeamHTML(
            Team.COLORADO_YETI,
            coloradoYetiRoster
    )

    @RequestMapping("/philadelphia_liberty")
    fun getPhiladelphiaLibertyRoster() = createTeamHTML(
            Team.PHILADELPHIA_LIBERTY,
            philadelphiaLibertyRoster
    )

    @RequestMapping("/yellowknife_wraiths")
    fun getYellowknifeWraithsRoster() = createTeamHTML(
            Team.YELLOWKNIFE_WRAITHS,
            yellowknifeWraithsRoster
    )

    @RequestMapping("/arizona_outlaws")
    fun getArizonaOutlawsRoster() = createTeamHTML(
            Team.ARIZONA_OUTLAWS,
            arizonaOutlawsRoster
    )

    @RequestMapping("/new_orleans_second_line")
    fun getNewOrleansSecondLineRoster() = createTeamHTML(
            Team.NEW_ORLEANS_SECOND_LINE,
            newOrleansSecondLineRoster
    )

    @RequestMapping("/orange_county_otters")
    fun getOrangeCountyOttersRoster() = createTeamHTML(
            Team.ORANGE_COUNTY_OTTERS,
            orangeCountyOttersRoster
    )

    @RequestMapping("/san_jose_sabercats")
    fun getSanJoseSaberCatsRoster() = createTeamHTML(
            Team.SAN_JOSE_SABERCATS,
            sanJoseSabercatsRoster
    )

    @RequestMapping("/palm_beach_solar_bears")
    fun getPalmBeachSolarBearsRoster() = createTeamHTML(
            Team.PALM_BEACH_SOLAR_BEARS,
            palmBeachSolarBearsRoster
    )

    @RequestMapping("/kansas_city_coyotes")
    fun getKansasCityCoyotesRoster() = createTeamHTML(
            Team.KANSAS_CITY_COYOTES,
            kansasCityCoyotesRoster
    )

    @RequestMapping("/portland_pythons")
    fun getPortlandPythonsRoster() = createTeamHTML(
            Team.PORTLAND_PYTHONS,
            portlandPythonsRoster
    )

    @RequestMapping("/norfolk_seawolves")
    fun getNorfolkSeawolvesRoster() = createTeamHTML(
            Team.NORFOLK_SEAWOLVES,
            norfolkSeawolvesRoster
    )

    @RequestMapping("/san_antonio_marshals")
    fun getSanAntonioMarshalsRoster() = createTeamHTML(
            Team.SAN_ANTONIO_MARSHALS,
            sanAntonioMarshalsRoster
    )

    @RequestMapping("/tijuana_luchadores")
    fun getTijuanaLuchadoresRoster() = createTeamHTML(
            Team.TIJUANA_LUCHADORES,
            tijuanaLuchadoresRoster
    )

    private fun createPlayersHTML(vararg playerLists: List<Player>) =
            PLAYERS_HTML.format(
                    "All Players",
                    playerLists.joinToString(",") { playerList ->
                        playerList.joinToString(",") {
                            "['${it.draftYear}','<a href=\"${it.team.url}\">${it.team.full}</a>'," +
                                    "'<a href=\"${it.url}\">${it.name}</a>','${it.position.full}','${it.tpe}']"
                        }
                    }
            )

    private fun createTeamHTML(team: Team, playerList: List<Player>): String {

        var teamInfo = "${team.full} - Total TPE: ${playerList.sumBy { it.tpe }}"
        if (team.dsfl) {
            teamInfo += " - Effective TPE: ${playerList.sumBy { if (it.tpe > 250) 250 else it.tpe }}"
        }

        return TEAM_HTML.format(
                team.full,
                playerList.joinToString(",") {
                    "['${it.draftYear}','<a href=\"${it.url}\">${it.name}</a>','${it.position.full}','${it.tpe}']"
                },
                teamInfo
        )
    }
}

class Player(
        val name: String,
        val team: Team,
        val position: Position,
        val draftYear: String,
        val tpe: Int,
        val url: String
)

enum class Team(
        val id: String,
        val url: String,
        val full: String,
        val dsfl: Boolean
) {
    BALTIMORE_HAWKS("69", "/baltimore_hawks", "Baltimore Hawks", false),
    COLORADO_YETI("55", "/colorado_yeti", "Colorado Yeti", false),
    PHILADELPHIA_LIBERTY("110", "/philadelphia_liberty", "Philadelphia Liberty", false),
    YELLOWKNIFE_WRAITHS("57", "/yellowknife_wraiths", "Yellowknife Wraiths", false),
    ARIZONA_OUTLAWS("72", "/arizona_outlaws", "Arizona Outlaws", false),
    NEW_ORLEANS_SECOND_LINE("113", "/new_orleans_second_line", "New Orleans Second Line", false),
    ORANGE_COUNTY_OTTERS("53", "/orange_county_otters", "Orange County Otters", false),
    SAN_JOSE_SABERCATS("51", "/san_jose_sabercats", "San Jose SaberCats", false),
    PALM_BEACH_SOLAR_BEARS("160", "/palm_beach_solar_bears", "Palm Beach Solar Bears", true),
    KANSAS_CITY_COYOTES("158", "/kansas_city_coyotes", "Kansas City Coyotes", true),
    PORTLAND_PYTHONS("164", "/portland_pythons", "Portland Pythons", true),
    NORFOLK_SEAWOLVES("162", "/norfolk_seawolves", "Norfolk SeaWolves", true),
    SAN_ANTONIO_MARSHALS("156", "/san_antonio_marshals", "San Antonio Marshals", true),
    TIJUANA_LUCHADORES("154", "/tijuana_luchadores", "Tijuana Luchadores", true)
}

enum class Position(
        val abbreviation: String,
        val full: String
) {
    QB("QB", "Quarterback"),
    RB("RB", "Running Back"),
    WR("WR", "Wide Receiver"),
    TE("TE", "Tight End"),
    OL("OL", "Offensive Line"),
    DE("DE", "Defensive End"),
    DT("DT", "Defensive Tackle"),
    LB("LB", "Linebacker"),
    CB("CB", "Cornerback"),
    S("S", "Safety"),
    KP("K/P", "Kicker/Punter"),
    UNKNOWN("", "Unknown");

    companion object {
        fun fromAbbreviation(abbreviation: String) =
                values().firstOrNull { it.abbreviation == abbreviation } ?: UNKNOWN
    }
}

fun main(args: Array<String>) {
    runApplication<TpeTrackerApplication>(*args)
}