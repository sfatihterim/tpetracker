package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.Logger
import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.regex.Pattern

class PlayerParser {

    fun parseAll() = ArrayList<ParsedPlayer>().apply {
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
                                    && it.contains(">(S")
                        }
                    }?.forEach { element ->
                        element.toString().let {
                            val playerInfo = parsePlayerInfo(it).split("?")
                            if (playerInfo.size == 3) {

                                val playerId = parsePlayerID(it)

                                val playerPost = connect("http://nsfl.jcink.net/index.php?showtopic=$playerId")
                                        .body()
                                        .getElementsByClass("post-normal")[0]
                                        .getElementsByClass("postcolor")
                                        .toString()

                                playerList.add(
                                        ParsedPlayer(
                                                playerId,
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
                                                parsePlayerAttribute(playerPost, "strength:"),
                                                parsePlayerAttribute(playerPost, "agility:"),
                                                parsePlayerAttribute(playerPost, "arm:"),
                                                parsePlayerAttribute(playerPost, "intelligence:"),
                                                parsePlayerAttribute(playerPost, "throwing accuracy:"),
                                                parsePlayerAttribute(playerPost, "tackling:"),
                                                parsePlayerAttribute(playerPost, "speed:"),
                                                parsePlayerAttribute(playerPost, "hands:"),
                                                parsePlayerAttribute(playerPost, "pass blocking:"),
                                                parsePlayerAttribute(playerPost, "run blocking:"),
                                                parsePlayerAttribute(playerPost, "endurance:"),
                                                parsePlayerAttribute(playerPost, "kick power:"),
                                                parsePlayerAttribute(playerPost, "kick accuracy:")
                                        )
                                )
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

    private fun connect(url: String): Document {
        while (true) {
            try {
                return Jsoup.connect(url).get()
            } catch (exception: Exception) {
                Logger.error("Jsoup.connect failed. $exception")
            }
        }
    }

    class ParsedPlayer(
            val id: String,
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
            val kickAccuracy: Int
    )
}