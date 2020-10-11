package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.Logger
import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class MyBBPlayerParser {

    private val inputDateFormat = SimpleDateFormat("MM-dd-yyyy',' hh:mm")
    private val lastSeenDateFormat = SimpleDateFormat("yyyy/MM/dd")

    fun parseActivePlayers() = ArrayList<ParsedPlayer>().apply {
        Team.values().forEach { addAll(parsePlayers(it)) }
    }

    fun parsePlayers(team: Team): List<ParsedPlayer> {

        val playerList = ArrayList<ParsedPlayer>()
        val documentList = ArrayList<Document>()

        val firstDocument = connect("https://forums.sim-football.com/forumdisplay.php?fid=${team.id}")
        documentList.add(firstDocument)

        val pageCount = parsePageCount(firstDocument.body().toString())
        if(pageCount>1){
            for (i in 2..pageCount) {
                println("Adding page $i")
                documentList.add(connect("https://forums.sim-football.com/forumdisplay.php?fid=${team.id}&page=${i}"))
            }
        }

        documentList.forEach { document ->
            document.body().getElementsByClass("inline_row")?.forEach { row -> row.allElements
                    ?.filter { element ->
                        element.toString().let {
                            it.startsWith("<td class=\"trow")
                                    && !it.contains("Moved:")
                                    && it.contains(">(S")
                        }
                    }?.forEach { element ->
                        element.toString().let {

                            val playerInfo = parsePlayerInfo(it).split("?")

                            if (playerInfo.size == 3) {

                                try{
                                    val playerId = parsePlayerID(it)
                                    val playerPostDoc = connect("https://forums.sim-football.com/showthread.php?tid=${playerId}")

                                    try {
                                        val playerPost = playerPostDoc.body()
                                                            .getElementsByClass("post")[0]

                                        val author = playerPost.getElementsByClass("post_author")[0]
                                        val user = author.getElementsByClass("largetext").text()
                                        val playerLink = author.getElementsByAttribute("href")[0].attr("href")
                                        val playerProfileDoc = connect(playerLink)
                                        val playerProfile = playerProfileDoc.body().getElementsByClass("trow2")

                                        val attributes = playerPost.getElementsByClass("post_body").toString()

                                        playerList.add(
                                                ParsedPlayer(
                                                        playerId,
                                                        user.replace("'", "’"),
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
                                                        parsePlayerTPE(it),
                                                        parsePlayerAttribute(attributes, "strength:"),
                                                        parsePlayerAttribute(attributes, "agility:"),
                                                        parsePlayerAttribute(attributes, "arm:"),
                                                        parsePlayerAttribute(attributes, "intelligence:"),
                                                        parsePlayerAttribute(attributes, "throwing accuracy:"),
                                                        parsePlayerAttribute(attributes, "tackling:"),
                                                        parsePlayerAttribute(attributes, "speed:"),
                                                        parsePlayerAttribute(attributes, "hands:"),
                                                        parsePlayerAttribute(attributes, "pass blocking:"),
                                                        parsePlayerAttribute(attributes, "run blocking:"),
                                                        parsePlayerAttribute(attributes, "endurance:"),
                                                        parsePlayerAttribute(attributes, "kick power:"),
                                                        parsePlayerAttribute(attributes, "kick accuracy:"),
                                                        parseLastSeen(playerProfile),
                                                        parsePlayerAttribute(attributes, "Height (ft.):"),
                                                        parsePlayerAttribute(attributes, "Weight (lbs.):")
                                                )
                                        )
                                } catch (exception: Exception) {
                                    Logger.error("Issue parsing player with Player ID: $playerId ! $exception")
                                }

                        } catch (exception: Exception){
                                    Logger.error("Issue parsing thread table row! $exception")
                                }
                            }
                    }
                  }
            }
        }

        return playerList
    }

    private fun parsePageCount(document: String): Int {
        return try {
            val startIndex = document.indexOf("Pages (")
            val endIndex = document.indexOf(")", startIndex)
            document.substring(startIndex, endIndex)
                    .replace(Pattern.compile("[^0-9.]").toRegex(), "")
                    .toInt()
        } catch (exception: Exception) {
            1
        }
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

    private fun parsePlayerTPE(elementString: String): Int {
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

    private fun parsePlayerID(elementString: String): String {

            val regex = """href="showthread.php\?tid=([0-9]+)">\(""".toRegex()
            val matchResult = regex.find(elementString) ?: throw Exception()
            return matchResult.groupValues[1]

    }

    fun parseUserName(playerID: String): String{
        try {
            val con_user = connect("https://forums.sim-football.com/showthread.php?tid=$playerID") ?: throw Exception()
            val user = con_user
                    .body()
                    .getElementsByClass("post")[0]
                    .getElementsByClass("post_author")[0]
                    .getElementsByClass("largetext")
                    .text()

            return user.replace("'", "’")
        } catch (exception: Exception) {
            return playerID
        }
    }

    private fun parseProfileId(elementString: String): String {
        val startIndex = elementString.indexOf("showuser=") + 9
        val endIndex = elementString.indexOf("\"", startIndex)
        return elementString.substring(startIndex, endIndex)
    }

    private fun parsePlayerAttribute(post: String, attributeName: String): Int {
        return try {

            var attribute = ""
            var started = false
            var finished = false
            var index = post.indexOf(attributeName, ignoreCase = true)

            if (attributeName == "Height (ft.):") {

                while (!finished) {

                    val char = post[index]

                    if (started && char.compareTo(10.toChar()) != 0) {
                        attribute += char
                    }else if (char.isDigit()) {
                        started = true
                        attribute += char
                    } else if (char.compareTo(10.toChar()) == 0) {
                        finished = true
                    }

                    index++
                }

                val regex = Regex("(\\d)\\D{0,5}(\\d{0,2})")
                var match = regex.find(attribute)

                if (match == null) {
                    attribute = "-999"
                } else {
                    var heightInInches = Integer.valueOf(match.groupValues[1]) * 12

                    if (match.groupValues[2].isNotEmpty()) {
                        heightInInches += Integer.valueOf(match.groupValues[2])
                    }

                    attribute = heightInInches.toString()
                }
            }

            while (!finished) {

                val char = post[index]

                if (char.isDigit()) {
                    started = true
                    attribute += char
                } else if (started) {
                    finished = true
                }

                index++
            }

            attribute.toInt()
        } catch (exception: Exception) {
            -999
        }
    }

    private fun parseLastSeen(profile_elements: Elements): String {

        val visit_elements = profile_elements.filter { element ->
            element.toString().contains("Last Visit:")
        }

        val lastSeenString = visit_elements[0].nextElementSibling().toString()

        if (lastSeenString.contains("(Hidden)"))
            return lastSeenDateFormat.format(inputDateFormat.parse("01-01-1900, 00:00"))

        val regex = "([0-9]{2}-[0-9]{2}-[0-9]{4}).*?([0-9]{2}:[0-9]{2} [PA]M)".toRegex()
        val matchResult = regex.find(lastSeenString) ?: throw Exception()
        return lastSeenDateFormat.format(inputDateFormat.parse(matchResult.groupValues[1] + ", " + matchResult.groupValues[2]))

    }

    private fun connect(url: String): Document {
        while (true) {
            try {
                return Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                        .get()
            } catch (exception: Exception) {
                Logger.error("Jsoup.connect failed. $exception")
            }
        }
    }

    data class ParsedPlayer(
            val id: String,
            val user: String,
            val name: String,
            val team: Team,
            val position: Position,
            val draftYear: String,
            val tpe: Int,
            val strength: Int,
            val agility: Int,
            val arm: Int,
            val intelligence: Int,
            val throwingAccuracy: Int,
            val tackling: Int,
            val speed: Int,
            val hands: Int,
            val passBlocking: Int,
            val runBlocking: Int,
            val endurance: Int,
            val kickPower: Int,
            val kickAccuracy: Int,
            val lastSeen: String,
            val height: Int,
            val weight: Int
    )
}
