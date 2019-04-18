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
            "<a href=\"/baltimore_hawks\">Baltimore Hawks</a><br><br>" +
                    "<a href=\"/colorado_yeti\">Colorado Yeti</a><br><br>" +
                    "<a href=\"/philadelphia_liberty\">Philadelphia Liberty</a><br><br>" +
                    "<a href=\"/yellowknife_wraiths\">Yellowknife Wraiths</a><br><br>" +
                    "<a href=\"/arizona_outlaws\">Arizona Outlaws</a><br><br>" +
                    "<a href=\"/new_orleans_second_line\">New Orleans Second Line</a><br><br>" +
                    "<a href=\"/orange_county_otters\">Orange County Otters</a><br><br>" +
                    "<a href=\"/san_jose_sabercats\">San Jose SaberCats</a><br><br>"

    @RequestMapping("/baltimore_hawks")
    fun getBaltimoreHawksRoster() = createHTLMString(parsePlayers("69", "Baltimore Hawks"))

    @RequestMapping("/colorado_yeti")
    fun getColoradoYetiRoster() = createHTLMString(parsePlayers("55", "Colorado Yeti"))

    @RequestMapping("/philadelphia_liberty")
    fun getPhiladelphiaLibertyRoster() = createHTLMString(parsePlayers("110", "Philadelphia Liberty"))

    @RequestMapping("/yellowknife_wraiths")
    fun getYellowknifeWraithsRoster() = createHTLMString(parsePlayers("57", "Yellowknife Wraiths"))

    @RequestMapping("/arizona_outlaws")
    fun getArizonaOutlawsRoster() = createHTLMString(parsePlayers("72", "Arizona Outlaws"))

    @RequestMapping("/new_orleans_second_line")
    fun getNewOrleansSecondLineRoster() = createHTLMString(parsePlayers("113", "New Orleans Second Line"))

    @RequestMapping("/orange_county_otters")
    fun getOrangeCountyOttersRoster() = createHTLMString(parsePlayers("53", "Orange County Otters"))

    @RequestMapping("/san_jose_sabercats")
    fun getSanJoseSaberCatsRoster() = createHTLMString(parsePlayers("51", "San Jose SaberCats"))

    private fun createHTLMString(playerList: List<Player>): String {

        val htmlStart = "<!DOCTYPE html><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css\"><script type=\"text/javascript\" language=\"javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js\"></script><script type=\"text/javascript\" language=\"javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js\"></script><script type=\"text/javascript\" class=\"init\">;var dataSet=["
        val htmlEnd = "];\$(document).ready(function(){\$('#example').DataTable({paging:false,data:dataSet,columns:[{ title: 'Draft Year' }, { title: 'Team' }, { title: 'Name' }, { title: 'Position' }, { title: 'TPE' }]})});</script><style>div{padding:8px}</style></head><body><div><table id=\"example\" class=\"ui celled table\" width=\"100%\"></table></div></body></html>"

        val playerData = playerList.joinToString(",") {
            "['${it.draftYear}','${it.team}','${it.name}','${it.position}','${it.tpe}']"
        }

        return htmlStart + playerData + htmlEnd
    }

    private fun parsePlayers(teamId: String, teamName: String): List<Player> {

        val playerList = ArrayList<Player>()

        listOf(
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=$teamId").get(),
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=$teamId&st=15").get(),
                Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=$teamId&st=30").get()
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
                                            teamName,
                                            playerInfo[1].trim(),
                                            playerInfo[2].trim(),
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
        val team: String,
        val name: String,
        val position: String,
        val tpe: Int
)

fun main(args: Array<String>) {
    runApplication<TpeTrackerApplication>(*args)
}