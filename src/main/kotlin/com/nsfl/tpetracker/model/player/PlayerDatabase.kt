package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team
import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.collections.ArrayList

class PlayerDatabase {

    fun updateActivePlayers(parsedPlayerList: List<MyBBPlayerParser.ParsedPlayer>): ArrayList<ActivePlayer> {

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

        val activePlayerList = ArrayList<ActivePlayer>()
        parsedPlayerList.forEach { player ->

            val tpeHistoryList = ArrayList<Pair<String, Int>>()
            tpeHistoryList.add(Pair("", 50))

            val resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM players WHERE player_id='${player.id}' ORDER BY id ASC"
            )

            while (resultSet.next()) {
                tpeHistoryList.add(
                        Pair(resultSet.getString("date"), resultSet.getInt("tpe"))
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

            activePlayerList.add(
                    ActivePlayer(
                            player.id,
                            player.user,
                            player.name,
                            player.team,
                            player.position,
                            player.draftYear,
                            player.tpe,
                            tpeHistoryList.maxBy { it.second }!!.second,
                            lastUpdated,
                            tpeHistoryList,
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
                            player.kickAccuracy,
                            player.lastSeen,
                            player.height,
                            player.weight
                    )
            )
        }

        connection.close()

        return activePlayerList
    }

    fun getRetiredPlayers(activePlayerList: ArrayList<ActivePlayer>): ArrayList<RetiredPlayer> {

        val connection = getConnection()

        val retiredPlayerIdList = ArrayList<String>()

        val retiredPlayersResultSet = connection.createStatement().executeQuery(
                "SELECT DISTINCT player_id FROM players WHERE " +
                        activePlayerList.joinToString(" AND ") { "player_id != '${it.id}'" }
        )

        while (retiredPlayersResultSet.next()) {
            retiredPlayerIdList.add(retiredPlayersResultSet.getString("player_id"))
        }

        val retiredPlayerList = ArrayList<RetiredPlayer>()

        retiredPlayerIdList.forEach { playerId ->

            var name: String? = null
            var team: Team? = null
            var position: Position? = null
            var draftYear: String? = null
            var currentTPE: Int? = null

            val tpeHistoryList = ArrayList<Pair<String, Int>>()
            tpeHistoryList.add(Pair("", 50))

            val playerResultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM players WHERE player_id='$playerId' ORDER BY id ASC"
            )

            var user = MyBBPlayerParser().parseUserName(playerId)

            while (playerResultSet.next()) {

                tpeHistoryList.add(
                        Pair(playerResultSet.getString("date"), playerResultSet.getInt("tpe"))
                )

                if (playerResultSet.isLast) {
                    name = playerResultSet.getString("name")
                    team = Team.fromName(playerResultSet.getString("team"))
                    position = Position.valueOf(playerResultSet.getString("position"))
                    draftYear = playerResultSet.getString("draft_year")
                    currentTPE = playerResultSet.getString("tpe").toInt()
                }
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

            retiredPlayerList.add(
                    RetiredPlayer(
                            playerId,
                            user,
                            name!!,
                            team!!,
                            position!!,
                            draftYear!!,
                            currentTPE!!,
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
                            }
                    )
            )
        }

        connection.close()

        return retiredPlayerList
    }

    fun initaliseAllPlayers(): ArrayList<ActivePlayer> {

        val connection = getConnection()

        val playerIdList = ArrayList<String>()

        val playersResultSet = connection.createStatement().executeQuery(
                "SELECT DISTINCT player_id FROM players"
        )

        while (playersResultSet.next()) {
            playerIdList.add(playersResultSet.getString("player_id"))
        }

        val playerList = ArrayList<ActivePlayer>()

        playerIdList.forEach { playerId ->

            var name: String? = null
            var team: Team? = null
            var position: Position? = null
            var draftYear: String? = null
            var currentTPE: Int? = null

            val tpeHistoryList = ArrayList<Pair<String, Int>>()
            tpeHistoryList.add(Pair("", 50))

            val playerResultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM players WHERE player_id='$playerId' ORDER BY id ASC"
            )

            while (playerResultSet.next()) {

                tpeHistoryList.add(
                        Pair(playerResultSet.getString("date"), playerResultSet.getInt("tpe"))
                )

                if (playerResultSet.isLast) {
                    name = playerResultSet.getString("name")
                    team = Team.fromName(playerResultSet.getString("team"))
                    position = Position.valueOf(playerResultSet.getString("position"))
                    draftYear = playerResultSet.getString("draft_year")
                    currentTPE = playerResultSet.getString("tpe").toInt()
                }
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
                    ActivePlayer(
                            playerId,
                            playerId,
                            name!!,
                            team!!,
                            position!!,
                            draftYear!!,
                            currentTPE!!,
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
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            "0",
                            0,
                            0
                    )
            )
        }

        connection.close()

        return playerList
    }
    private fun getConnection() = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tpetracker_local?user=tpetracker_local&password=tpetracker_local")
   // private fun getConnection() = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))
}
