package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.regex.Pattern

class PlayerParser {

    fun parseAll() = ArrayList<Player>().apply {
        addAll(parsePlayers(Team.BALTIMORE_HAWKS, 4))
        addAll(parsePlayers(Team.COLORADO_YETI, 4))
        addAll(parsePlayers(Team.PHILADELPHIA_LIBERTY, 4))
        addAll(parsePlayers(Team.YELLOWKNIFE_WRAITHS, 4))
        addAll(parsePlayers(Team.ARIZONA_OUTLAWS, 4))
        addAll(parsePlayers(Team.NEW_ORLEANS_SECOND_LINE, 4))
        addAll(parsePlayers(Team.ORANGE_COUNTY_OTTERS, 4))
        addAll(parsePlayers(Team.SAN_JOSE_SABERCATS, 4))
        addAll(parsePlayers(Team.PALM_BEACH_SOLAR_BEARS, 4))
        addAll(parsePlayers(Team.KANSAS_CITY_COYOTES, 4))
        addAll(parsePlayers(Team.PORTLAND_PYTHONS, 4))
        addAll(parsePlayers(Team.NORFOLK_SEAWOLVES, 4))
        addAll(parsePlayers(Team.SAN_ANTONIO_MARSHALS, 4))
        addAll(parsePlayers(Team.TIJUANA_LUCHADORES, 4))
        addAll(parsePlayers(Team.FREE_AGENTS, 5))
        addAll(parsePlayers(Team.QB_PROSPECTS, 3))
        addAll(parsePlayers(Team.RB_PROSPECTS, 3))
        addAll(parsePlayers(Team.WR_PROSPECTS, 3))
        addAll(parsePlayers(Team.TE_PROSPECTS, 3))
        addAll(parsePlayers(Team.OL_PROSPECTS, 3))
        addAll(parsePlayers(Team.DE_PROSPECTS, 3))
        addAll(parsePlayers(Team.DT_PROSPECTS, 3))
        addAll(parsePlayers(Team.LB_PROSPECTS, 3))
        addAll(parsePlayers(Team.CB_PROSPECTS, 3))
        addAll(parsePlayers(Team.S_PROSPECTS, 3))
        addAll(parsePlayers(Team.KP_PROSPECTS, 3))
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
}