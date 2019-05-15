package com.nsfl.tpetracker.model.player

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

        val firstDocument = Jsoup.connect("http://nsfl.jcink.net/index.php?showforum=${team.id}").get()
        documentList.add(firstDocument)

        val pageCount = parsePageCount(firstDocument.body().toString())

        for (i in 1..(pageCount - 1)) {
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
                                        ParsedPlayer(
                                                parsePlayerID(it),
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
                                                parseTPE(it)
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

    private fun parsePlayerID(elementString: String): String {
        val startIndex = elementString.indexOf("showtopic=") + 10
        val endIndex = elementString.indexOf("\"", startIndex)
        return elementString.substring(startIndex, endIndex)
    }

    class ParsedPlayer(
            val id: String,
            val name: String,
            val team: Team,
            val position: Position,
            val draftYear: String,
            val tpe: Int
    )
}