package com.nsfl.tpetracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.jsoup.Jsoup
import java.util.regex.Pattern

@RestController
@SpringBootApplication
class TpeTrackerApplication {

    @RequestMapping
    fun getIndex() =
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
    fun getAllPlayers() = createPlayersHTLM(
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
    fun getBaltimoreHawksRoster() = createTeamHTLM(parsePlayers(Team.BALTIMORE_HAWKS))

    @RequestMapping("/colorado_yeti")
    fun getColoradoYetiRoster() = createTeamHTLM(parsePlayers(Team.COLORADO_YETI))

    @RequestMapping("/philadelphia_liberty")
    fun getPhiladelphiaLibertyRoster() = createTeamHTLM(parsePlayers(Team.PHILADELPHIA_LIBERTY))

    @RequestMapping("/yellowknife_wraiths")
    fun getYellowknifeWraithsRoster() = createTeamHTLM(parsePlayers(Team.YELLOWKNIFE_WRAITHS))

    @RequestMapping("/arizona_outlaws")
    fun getArizonaOutlawsRoster() = createTeamHTLM(parsePlayers(Team.ARIZONA_OUTLAWS))

    @RequestMapping("/new_orleans_second_line")
    fun getNewOrleansSecondLineRoster() = createTeamHTLM(parsePlayers(Team.NEW_ORLEANS_SECOND_LINE))

    @RequestMapping("/orange_county_otters")
    fun getOrangeCountyOttersRoster() = createTeamHTLM(parsePlayers(Team.ORANGE_COUNTY_OTTERS))

    @RequestMapping("/san_jose_sabercats")
    fun getSanJoseSaberCatsRoster() = createTeamHTLM(parsePlayers(Team.SAN_JOSE_SABERCATS))

    private fun createPlayersHTLM(vararg playerLists: List<Player>): String {

        val htmlStart = "<!DOCTYPE html><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=["
        val htmlEnd = "];\$(document).ready(function(){\$('#example').DataTable({paging:false,order: [[4, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Team' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding:8px}</style></head><body><div><table id=\"example\" class=\"ui celled table\" width=\"100%\"></table></div></body></html>"

        val playerData = playerLists.joinToString(",") { playerList ->
            playerList.joinToString(",") {
                "['${it.draftYear}','${it.teamName}','${it.name}','${it.position}','${it.tpe}']"

            }
        }

        return htmlStart + playerData + htmlEnd
    }

    private fun createTeamHTLM(playerList: List<Player>): String {

        val htmlStart = "<!DOCTYPE html><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=["
        val htmlEnd = "];\$(document).ready(function(){\$('#example').DataTable({paging:false,order: [[3, \"desc\"]],data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding:8px}</style></head><body><div><table id=\"example\" class=\"ui celled table\" width=\"100%\"></table></div></body></html>"

        val playerData = playerList.joinToString(",") {
            "['${it.draftYear}','${it.name}','${it.position}','${it.tpe}']"
        }

        return htmlStart + playerData + htmlEnd
    }

    private fun parsePlayers(team: Team): List<Player> {

        val playerList = ArrayList<Player>()

        listOf(
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=${team.teamId}").get(),
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=${team.teamId}&st=15").get(),
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=${team.teamId}&st=30").get()
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
                            val tpe = parseTPE(it)
                            playerList.add(
                                    Player(
                                            playerInfo[0].trim(),
                                            team.teamName,
                                            playerInfo[1].trim(),
                                            Position.fromAbbreviation(playerInfo[2].trim()),
                                            tpe
                                    )
                            )
                        }
                    }
        }

        return playerList
    }

    private fun parsePlayerInfo(elementString: String): String {

        val startIndex = elementString.indexOf(">(S") + 3
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
}

class Player(
        val draftYear: String,
        val teamName: String,
        val name: String,
        val position: String,
        val tpe: Int
)

enum class Team(
        val teamId: String,
        val teamName: String
) {
    BALTIMORE_HAWKS("69", "Baltimore Hawks"),
    COLORADO_YETI("55", "Colorado Yeti"),
    PHILADELPHIA_LIBERTY("110", "Philadelphia Liberty"),
    YELLOWKNIFE_WRAITHS("57", "Yellowknife Wraiths"),
    ARIZONA_OUTLAWS("72", "Arizona Outlaws"),
    NEW_ORLEANS_SECOND_LINE("113", "New Orleans Second Line"),
    ORANGE_COUNTY_OTTERS("53", "Orange County Otters"),
    SAN_JOSE_SABERCATS("51", "San Jose SaberCats")
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
    KP("K/P", "Kicker/Punter");

    companion object {
        fun fromAbbreviation(abbreviation: String) =
                values().firstOrNull { it.abbreviation == abbreviation }?.full ?: ""
    }
}

fun main(args: Array<String>) {
    runApplication<TpeTrackerApplication>(*args)
}