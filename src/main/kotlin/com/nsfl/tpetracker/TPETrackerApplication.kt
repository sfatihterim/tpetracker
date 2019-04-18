package com.nsfl.tpetracker

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.regex.Pattern

private const val PLAYERS_HTML = "<!DOCTYPE html><html><head><title>%s</title><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=[%s];\$(document).ready(function(){\$('#table').DataTable({paging:false,order: [[4, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Team' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding-left: 5%%; padding-right: 5%%; padding-top: 0.5%%; padding-bottom: 0.5%%;}</style></head><body><div><table id=\"table\" class=\"ui celled table\" width=\"100%%\"></table></div></body></html>"
private const val TEAM_HTML = "<!DOCTYPE html><html><head><title>%s</title><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=[%s];\$(document).ready(function(){\$('#table').DataTable({paging:false,order: [[3, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding-left: 5%%; padding-right: 5%%; padding-top: 0.5%%; padding-bottom: 0.5%%;}caption{padding-bottom: 30px; font-size: 30px}</style></head><body><div><table id=\"table\" class=\"ui celled table\" width=\"100%%\"><caption>%s</caption></table></div></body></html>"

@RestController
@SpringBootApplication
@EnableScheduling
class TPETrackerApplication {

    private val logger = LoggerFactory.getLogger("TPETrackerApplication ")

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
    private var freeAgentsRoster = ArrayList<Player>()
    private var qbProspectsRoster = ArrayList<Player>()
    private var rbProspectsRoster = ArrayList<Player>()
    private var wrProspectsRoster = ArrayList<Player>()
    private var teProspectsRoster = ArrayList<Player>()
    private var olProspectsRoster = ArrayList<Player>()
    private var deProspectsRoster = ArrayList<Player>()
    private var dtProspectsRoster = ArrayList<Player>()
    private var lbProspectsRoster = ArrayList<Player>()
    private var cbProspectsRoster = ArrayList<Player>()
    private var sProspectsRoster = ArrayList<Player>()
    private var kpProspectsRoster = ArrayList<Player>()

    @Scheduled(fixedRate = 6 * 60 * 60000)
    fun refreshData() {

        logger.info("Scheduled data refresh started.")

        baltimoreHawksRoster = parsePlayers(Team.BALTIMORE_HAWKS, 4)
        coloradoYetiRoster = parsePlayers(Team.COLORADO_YETI, 4)
        philadelphiaLibertyRoster = parsePlayers(Team.PHILADELPHIA_LIBERTY, 4)
        yellowknifeWraithsRoster = parsePlayers(Team.YELLOWKNIFE_WRAITHS, 4)
        arizonaOutlawsRoster = parsePlayers(Team.ARIZONA_OUTLAWS, 4)
        newOrleansSecondLineRoster = parsePlayers(Team.NEW_ORLEANS_SECOND_LINE, 4)
        orangeCountyOttersRoster = parsePlayers(Team.ORANGE_COUNTY_OTTERS, 4)
        sanJoseSabercatsRoster = parsePlayers(Team.SAN_JOSE_SABERCATS, 4)
        palmBeachSolarBearsRoster = parsePlayers(Team.PALM_BEACH_SOLAR_BEARS, 4)
        kansasCityCoyotesRoster = parsePlayers(Team.KANSAS_CITY_COYOTES, 4)
        portlandPythonsRoster = parsePlayers(Team.PORTLAND_PYTHONS, 4)
        norfolkSeawolvesRoster = parsePlayers(Team.NORFOLK_SEAWOLVES, 4)
        sanAntonioMarshalsRoster = parsePlayers(Team.SAN_ANTONIO_MARSHALS, 4)
        tijuanaLuchadoresRoster = parsePlayers(Team.TIJUANA_LUCHADORES, 4)
        freeAgentsRoster = parsePlayers(Team.FREE_AGENTS, 5)
        qbProspectsRoster = parsePlayers(Team.QB_PROSPECTS, 3)
        rbProspectsRoster = parsePlayers(Team.RB_PROSPECTS, 3)
        wrProspectsRoster = parsePlayers(Team.WR_PROSPECTS, 3)
        teProspectsRoster = parsePlayers(Team.TE_PROSPECTS, 3)
        olProspectsRoster = parsePlayers(Team.OL_PROSPECTS, 3)
        deProspectsRoster = parsePlayers(Team.DE_PROSPECTS, 3)
        dtProspectsRoster = parsePlayers(Team.DT_PROSPECTS, 3)
        lbProspectsRoster = parsePlayers(Team.LB_PROSPECTS, 3)
        cbProspectsRoster = parsePlayers(Team.CB_PROSPECTS, 3)
        sProspectsRoster = parsePlayers(Team.S_PROSPECTS, 3)
        kpProspectsRoster = parsePlayers(Team.KP_PROSPECTS, 3)

        logger.info("Scheduled data refresh finished.")
    }

    private fun parsePlayers(team: Team, pageCount: Int): ArrayList<Player> {

        val playerList = ArrayList<Player>()
        val documentList = ArrayList<Document>()

        for (i in 0..(pageCount - 1)) {
            documentList.add(
                    Jsoup.connect(
                            "http://nsfl.jcink.net/index.php?showforum=${team.id}&st=${i * 15}"
                    ).get()
            )
        }

        documentList.forEach { document ->
            document.body().getElementById("topic-list")?.allElements
                    ?.filter { element ->
                        element.toString().let {
                            it.startsWith("<td class=\"row4\"")
                                    && it.contains(">(S")
                        }
                    }?.forEach { element ->
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
                    "<a href=\"/tijuana_luchadores\">Tijuana Luchadores</a><br><br>" +
                    "<a href=\"/free_agents\">Free Agents</a><br><br>" +
                    "<a href=\"/qb_prospects\">QB Prospects</a><br><br>" +
                    "<a href=\"/rb_prospects\">RB Prospects</a><br><br>" +
                    "<a href=\"/wr_prospects\">WR Prospects</a><br><br>" +
                    "<a href=\"/te_prospects\">TE Prospects</a><br><br>" +
                    "<a href=\"/ol_prospects\">OL Prospects</a><br><br>" +
                    "<a href=\"/de_prospects\">DE Prospects</a><br><br>" +
                    "<a href=\"/dt_prospects\">DT Prospects</a><br><br>" +
                    "<a href=\"/lb_prospects\">LB Prospects</a><br><br>" +
                    "<a href=\"/cb_prospects\">CB Prospects</a><br><br>" +
                    "<a href=\"/s_prospects\">S Prospects</a><br><br>" +
                    "<a href=\"/kp_prospects\">K/P Prospects</a><br><br>"

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
            tijuanaLuchadoresRoster,
            freeAgentsRoster,
            qbProspectsRoster,
            rbProspectsRoster,
            wrProspectsRoster,
            teProspectsRoster,
            olProspectsRoster,
            deProspectsRoster,
            dtProspectsRoster,
            lbProspectsRoster,
            cbProspectsRoster,
            sProspectsRoster,
            kpProspectsRoster
    )

    @RequestMapping("/baltimore_hawks")
    fun getBaltimoreHawksRoster() = createTeamHTML(Team.BALTIMORE_HAWKS, baltimoreHawksRoster)

    @RequestMapping("/colorado_yeti")
    fun getColoradoYetiRoster() = createTeamHTML(Team.COLORADO_YETI, coloradoYetiRoster)

    @RequestMapping("/philadelphia_liberty")
    fun getPhiladelphiaLibertyRoster() = createTeamHTML(Team.PHILADELPHIA_LIBERTY, philadelphiaLibertyRoster)

    @RequestMapping("/yellowknife_wraiths")
    fun getYellowknifeWraithsRoster() = createTeamHTML(Team.YELLOWKNIFE_WRAITHS, yellowknifeWraithsRoster)

    @RequestMapping("/arizona_outlaws")
    fun getArizonaOutlawsRoster() = createTeamHTML(Team.ARIZONA_OUTLAWS, arizonaOutlawsRoster)

    @RequestMapping("/new_orleans_second_line")
    fun getNewOrleansSecondLineRoster() = createTeamHTML(Team.NEW_ORLEANS_SECOND_LINE, newOrleansSecondLineRoster)

    @RequestMapping("/orange_county_otters")
    fun getOrangeCountyOttersRoster() = createTeamHTML(Team.ORANGE_COUNTY_OTTERS, orangeCountyOttersRoster)

    @RequestMapping("/san_jose_sabercats")
    fun getSanJoseSaberCatsRoster() = createTeamHTML(Team.SAN_JOSE_SABERCATS, sanJoseSabercatsRoster)

    @RequestMapping("/palm_beach_solar_bears")
    fun getPalmBeachSolarBearsRoster() = createTeamHTML(Team.PALM_BEACH_SOLAR_BEARS, palmBeachSolarBearsRoster)

    @RequestMapping("/kansas_city_coyotes")
    fun getKansasCityCoyotesRoster() = createTeamHTML(Team.KANSAS_CITY_COYOTES, kansasCityCoyotesRoster)

    @RequestMapping("/portland_pythons")
    fun getPortlandPythonsRoster() = createTeamHTML(Team.PORTLAND_PYTHONS, portlandPythonsRoster)

    @RequestMapping("/norfolk_seawolves")
    fun getNorfolkSeawolvesRoster() = createTeamHTML(Team.NORFOLK_SEAWOLVES, norfolkSeawolvesRoster)

    @RequestMapping("/san_antonio_marshals")
    fun getSanAntonioMarshalsRoster() = createTeamHTML(Team.SAN_ANTONIO_MARSHALS, sanAntonioMarshalsRoster)

    @RequestMapping("/tijuana_luchadores")
    fun getTijuanaLuchadoresRoster() = createTeamHTML(Team.TIJUANA_LUCHADORES, tijuanaLuchadoresRoster)

    @RequestMapping("/free_agents")
    fun getFreeAgentsRoster() = createTeamHTML(Team.FREE_AGENTS, freeAgentsRoster)

    @RequestMapping("/qb_prospects")
    fun getQBProspectsRoster() = createTeamHTML(Team.QB_PROSPECTS, qbProspectsRoster)

    @RequestMapping("/rb_prospects")
    fun getRBProspectsRoster() = createTeamHTML(Team.RB_PROSPECTS, rbProspectsRoster)

    @RequestMapping("/wr_prospects")
    fun getWRProspectsRoster() = createTeamHTML(Team.WR_PROSPECTS, wrProspectsRoster)

    @RequestMapping("/te_prospects")
    fun getTEProspectsRoster() = createTeamHTML(Team.TE_PROSPECTS, teProspectsRoster)

    @RequestMapping("/ol_prospects")
    fun getOLProspectsRoster() = createTeamHTML(Team.OL_PROSPECTS, olProspectsRoster)

    @RequestMapping("/de_prospects")
    fun getDEProspectsRoster() = createTeamHTML(Team.DE_PROSPECTS, deProspectsRoster)

    @RequestMapping("/dt_prospects")
    fun getDTProspectsRoster() = createTeamHTML(Team.DT_PROSPECTS, dtProspectsRoster)

    @RequestMapping("/lb_prospects")
    fun getLBProspectsRoster() = createTeamHTML(Team.LB_PROSPECTS, lbProspectsRoster)

    @RequestMapping("/cb_prospects")
    fun getCBProspectsRoster() = createTeamHTML(Team.CB_PROSPECTS, cbProspectsRoster)

    @RequestMapping("/s_prospects")
    fun getSProspectsRoster() = createTeamHTML(Team.S_PROSPECTS, sProspectsRoster)

    @RequestMapping("/kp_prospects")
    fun getKPProspectsRoster() = createTeamHTML(Team.KP_PROSPECTS, kpProspectsRoster)

    private fun createPlayersHTML(vararg playerLists: List<Player>) =
            PLAYERS_HTML.format(
                    "All Players",
                    playerLists.filter { it.isNotEmpty() }.joinToString(",") { playerList ->
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
    TIJUANA_LUCHADORES("154", "/tijuana_luchadores", "Tijuana Luchadores", true),
    FREE_AGENTS("34", "/free_agents", "Free Agents", false),
    QB_PROSPECTS("", "/qb_prospects", "QB Prospects", false),
    RB_PROSPECTS("", "/rb_prospects", "RB Prospects", false),
    WR_PROSPECTS("", "/wr_prospects", "WR Prospects", false),
    TE_PROSPECTS("", "/te_prospects", "TE Prospects", false),
    OL_PROSPECTS("", "/ol_prospects", "OL Prospects", false),
    DE_PROSPECTS("", "/de_prospects", "DE Prospects", false),
    DT_PROSPECTS("", "/dt_prospects", "DT Prospects", false),
    LB_PROSPECTS("", "/lb_prospects", "LB Prospects", false),
    CB_PROSPECTS("", "/cb_prospects", "CB Prospects", false),
    S_PROSPECTS("", "/s_prospects", "S Prospects", false),
    KP_PROSPECTS("", "/kp_prospects", "K/P Prospects", false)
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
    runApplication<TPETrackerApplication>(*args)
}