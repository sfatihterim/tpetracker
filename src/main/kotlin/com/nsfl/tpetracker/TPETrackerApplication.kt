package com.nsfl.tpetracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.jsoup.Jsoup
import java.util.regex.Pattern

private const val PLAYERS_HTML = "<!DOCTYPE html><html><head><title>%s</title><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=[%s];\$(document).ready(function(){\$('#example').DataTable({paging:false,order: [[4, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Team' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding:8px}</style></head><body><div><table id=\"example\" class=\"ui celled table\" width=\"100%%\"></table></div></body></html>"
private const val TEAM_HTML = "<!DOCTYPE html><html><head><title>%s</title><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=[%s];\$(document).ready(function(){\$('#example').DataTable({paging:false,order: [[3, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding:8px}</style></head><body><div><h1>%s</h1><table id=\"example\" class=\"ui celled table\" width=\"100%%\"></table></div></body></html>"

@RestController
@SpringBootApplication
class TpeTrackerApplication {

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
                    "<a href=\"/san_jose_sabercats\">San Jose SaberCats</a><br><br>"

    @RequestMapping("/all_players")
    fun getAllPlayers() = createPlayersHTML(
            parsePlayers(Team.BALTIMORE_HAWKS),
            parsePlayers(Team.COLORADO_YETI),
            parsePlayers(Team.PHILADELPHIA_LIBERTY),
            parsePlayers(Team.YELLOWKNIFE_WRAITHS),
            parsePlayers(Team.ARIZONA_OUTLAWS),
            parsePlayers(Team.NEW_ORLEANS_SECOND_LINE),
            parsePlayers(Team.ORANGE_COUNTY_OTTERS),
            parsePlayers(Team.SAN_JOSE_SABERCATS)
    )

    @RequestMapping("/baltimore_hawks")
    fun getBaltimoreHawksRoster() = createTeamHTML(Team.BALTIMORE_HAWKS)

    @RequestMapping("/colorado_yeti")
    fun getColoradoYetiRoster() = createTeamHTML(Team.COLORADO_YETI)

    @RequestMapping("/philadelphia_liberty")
    fun getPhiladelphiaLibertyRoster() = createTeamHTML(Team.PHILADELPHIA_LIBERTY)

    @RequestMapping("/yellowknife_wraiths")
    fun getYellowknifeWraithsRoster() = createTeamHTML(Team.YELLOWKNIFE_WRAITHS)

    @RequestMapping("/arizona_outlaws")
    fun getArizonaOutlawsRoster() = createTeamHTML(Team.ARIZONA_OUTLAWS)

    @RequestMapping("/new_orleans_second_line")
    fun getNewOrleansSecondLineRoster() = createTeamHTML(Team.NEW_ORLEANS_SECOND_LINE)

    @RequestMapping("/orange_county_otters")
    fun getOrangeCountyOttersRoster() = createTeamHTML(Team.ORANGE_COUNTY_OTTERS)

    @RequestMapping("/san_jose_sabercats")
    fun getSanJoseSaberCatsRoster() = createTeamHTML(Team.SAN_JOSE_SABERCATS)

    private fun createPlayersHTML(vararg playerLists: List<Player>) =
            PLAYERS_HTML.format(
                    "All Players",
                    playerLists.joinToString(",") { playerList ->
                        playerList.joinToString(",") {
                            "['${it.draftYear}','<a href=\"${it.team.url}\">${it.team.full}</a>','<a href=\"${it.url}\">${it.name}</a>','${it.position.full}','${it.tpe}']"
                        }
                    }
            )

    private fun createTeamHTML(team: Team): String {

        val playerList = parsePlayers(team)

        return TEAM_HTML.format(
                team.full,
                playerList.joinToString(",") {
                    "['${it.draftYear}','<a href=\"${it.url}\">${it.name}</a>','${it.position.full}','${it.tpe}']"
                },
                "${team.full} - Total TPE: ${playerList.sumBy { it.tpe }}"
        )
    }

    private fun parsePlayers(team: Team): List<Player> {

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
                                    && it.contains("TPE:")
                        }
                    }.forEach { element ->
                        element.toString().let {
                            val playerInfo = parsePlayerInfo(it).split("-")
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
    }

    private fun parseTPE(elementString: String): Int {

        val startIndex = elementString.indexOf("TPE:")
        val endIndex = elementString.indexOf("<", startIndex)

        return elementString.substring(startIndex, endIndex)
                .replace(Pattern.compile("[^0-9.]").toRegex(), "")
                .toInt()
    }

    private fun parseUrl(elementString: String): String {
        val startIndex = elementString.indexOf("href=") + 6
        val endIndex = elementString.indexOf("\"", startIndex)
        return elementString.substring(startIndex, endIndex)
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
        val full: String
) {
    BALTIMORE_HAWKS("69", "/baltimore_hawks", "Baltimore Hawks"),
    COLORADO_YETI("55", "/colorado_yeti", "Colorado Yeti"),
    PHILADELPHIA_LIBERTY("110", "/philadelphia_liberty", "Philadelphia Liberty"),
    YELLOWKNIFE_WRAITHS("57", "/yellowknife_wraiths", "Yellowknife Wraiths"),
    ARIZONA_OUTLAWS("72", "/arizona_outlaws", "Arizona Outlaws"),
    NEW_ORLEANS_SECOND_LINE("113", "/new_orleans_second_line", "New Orleans Second Line"),
    ORANGE_COUNTY_OTTERS("53", "/orange_county_otters", "Orange County Otters"),
    SAN_JOSE_SABERCATS("51", "/san_jose_sabercats", "San Jose SaberCats")
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