package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.Logger
import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class PlayerParser {

    private val inputDateFormat = SimpleDateFormat("MMM dd yyyy, HH:mm a")
    private val lastSeenDateFormat = SimpleDateFormat("yyyy/MM/dd")

    fun parseActivePlayers() = ArrayList<ParsedPlayer>().apply {
        Team.values().forEach { addAll(parsePlayers(it)) }
    }

    private fun parsePlayers(team: Team): List<ParsedPlayer> {

        val playerList = ArrayList<ParsedPlayer>()
        val documentList = ArrayList<Document>()

        val firstDocument = connect("http://nsfl.jcink.net/index.php?showforum=${team.id}")
        documentList.add(firstDocument)

        val pageCount = parsePageCount(firstDocument.body().toString())

        for (i in 1..(pageCount - 1)) {
            documentList.add(connect("http://nsfl.jcink.net/index.php?showforum=${team.id}&st=${i * 15}"))
        }

        documentList.forEach { document ->
            document.body().getElementById("topic-list")?.allElements
                    ?.filter { element ->
                        element.toString().let {
                            it.startsWith("<td class=\"row4\"")
                                    && !it.contains("Moved:")
                                    && it.contains(">(S")
                        }
                    }?.forEach { element ->
                        element.toString().let {

                            val playerInfo = parsePlayerInfo(it).split("?")

                            if (playerInfo.size == 3) {

                                val playerId = parsePlayerID(it)

                                val playerPostDoc = connect("http://nsfl.jcink.net/index.php?showtopic=$playerId")

                                try {
                                    val playerPost = playerPostDoc.body().getElementsByClass("post-normal")[0]

                                    val user = playerPost.getElementsByClass("normalname").text()
                                    val attributes = playerPost.getElementsByClass("postcolor").toString()

                                    val profileId = parseProfileId(playerPost.getElementsByClass("normalname").toString())
                                    val playerProfile = connect("http://nsfl.jcink.net/index.php?showuser=$profileId")

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
                                                    parseLastSeen(playerProfile.getElementById("profile-statistics").toString())
                                            )
                                    )
                            } catch (exception: Exception) {
                                Logger.error("Issue parsing player with Player ID: $playerId ! $exception")
                            }
                        }
                    }
                  }
        }

        return playerList
    }

    private fun parsePageCount(document: String): Int {
        return try {
            val startIndex = document.indexOf("Pages:</a>")
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
        val startIndex = elementString.indexOf("showtopic=") + 10
        val endIndex = elementString.indexOf("\"", startIndex)
        return elementString.substring(startIndex, endIndex)
    }

    fun parseUserName(playerID: String): String{
      try {
        val user = connect("http://nsfl.jcink.net/index.php?showtopic=$playerID")
              .body()
              .getElementsByClass("post-normal")[0]
              .getElementsByClass("normalname")
              .text()
        return user.replace("'", "’")
      } catch (exception: Exception) {
        return "-"
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

    private fun parseLastSeen(elementString: String): String {

        val startIndex = elementString.indexOf("Last Seen:") + 10
        val endIndex = elementString.indexOf("\n", startIndex)
        val lastSeenString = elementString.substring(startIndex, endIndex).trim()

        return try {
            lastSeenDateFormat.format(inputDateFormat.parse(lastSeenString))
        } catch (exception: Exception) {
            if (lastSeenString.startsWith("Yesterday")) {
                lastSeenDateFormat.format(
                        Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }.timeInMillis
                )
            } else {
                lastSeenDateFormat.format(Calendar.getInstance().timeInMillis)
            }
        }
    }

    private fun connect(url: String): Document {
        while (true) {
            try {
                return Jsoup
                        .connect(url)
                        // sorry jcink, i'm spoofing my user agent
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0")
                        .get()
            } catch (exception: Exception) {
                Logger.error("Jsoup.connect failed. $exception")
                Thread.sleep(60000) // don't immediately retry
            }
        }
    }

    class ParsedPlayer(
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
            val lastSeen: String
    )
}
