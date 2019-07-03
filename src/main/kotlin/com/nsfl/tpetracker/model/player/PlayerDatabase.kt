package com.nsfl.tpetracker.model.player

import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.collections.ArrayList

class PlayerDatabase {

    fun updatePlayers(parsedPlayerList: List<PlayerParser.ParsedPlayer>): ArrayList<Player> {

        val saturday = SimpleDateFormat("MM/dd/yyyy").format(
                Calendar.getInstance().apply {
                    if (get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                        set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                        add(Calendar.DAY_OF_YEAR, -7)
                    }
                }.timeInMillis
        )

        val values = parsedPlayerList.joinToString(",") {
            "(DEFAULT,'${it.id}','$saturday','${it.draftYear}','${it.team}','${it.name}','${it.position}','${it.tpe}')"
        }

        val connection = getConnection()
        connection.createStatement().execute("DELETE FROM players WHERE date='$saturday'")
        connection.createStatement().execute("INSERT INTO players VALUES %s;".format(values))

        val playerList = ArrayList<Player>()
        parsedPlayerList.forEach { player ->

            val tpeHistoryList = ArrayList<Pair<String, Int>>()
            tpeHistoryList.add(Pair("", 50))

            val ruleSet = connection.createStatement().executeQuery(
                    "SELECT * FROM players WHERE player_id='${player.id}' ORDER BY id ASC"
            )

            while (ruleSet.next()) {
                tpeHistoryList.add(
                        Pair(ruleSet.getString("date"), ruleSet.getInt("tpe"))
                )
            }

            var lastUpdated = "-"
            var i = tpeHistoryList.lastIndex

            while (i > 0) {
                if (tpeHistoryList[i].second != tpeHistoryList[i - 1].second) {
                    lastUpdated = tpeHistoryList[i].first.substring(6) + "/" + tpeHistoryList[i].first.substring(0, 5)
                    i = 0
                } else {
                    i--
                }
            }

            playerList.add(
                    Player(
                            player.id,
                            player.user,
                            player.name,
                            player.team,
                            player.position,
                            player.draftYear,
                            player.tpe,
                            tpeHistoryList.maxBy { it.second }!!.second,
                            lastUpdated,
                            tpeHistoryList.mapIndexed { index, pair ->
                                Pair(
                                        if (index == 0) {
                                            pair.first
                                        } else {
                                            pair.first.substring(0, 5)
                                        },
                                        pair.second
                                )
                            },
                            player.strength,
                            player.agility,
                            player.arm,
                            player.intelligence,
                            player.throwingAccuracy,
                            player.tackling,
                            player.speed,
                            player.hands,
                            player.passBlocking,
                            player.runBlocking,
                            player.endurance,
                            player.kickPower,
                            player.kickAccuracy
                    )
            )
        }

        connection.close()

        return playerList
    }

    private fun getConnection() = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))
}