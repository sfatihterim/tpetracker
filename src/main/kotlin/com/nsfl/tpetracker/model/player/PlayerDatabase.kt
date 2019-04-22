package com.nsfl.tpetracker.model.player

import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.arrayListOf
import kotlin.collections.joinToString

class PlayerDatabase {

    fun updatePlayers(playerList: List<Player>) {

        val saturday = SimpleDateFormat("MM/dd/yyyy").format(
                Calendar.getInstance().apply {
                    if (get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                        set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                        add(Calendar.DAY_OF_YEAR, -7)
                    }
                }.timeInMillis
        )

        val values = playerList.joinToString(",") {
            "(DEFAULT,'${it.id}','$saturday','${it.draftYear}','${it.team}','${it.name}','${it.position}','${it.tpe}')"
        }

        getConnection().let {
            it.createStatement().execute("DELETE FROM players WHERE date='$saturday'")
            it.createStatement().execute("INSERT INTO players VALUES %s;".format(values))
            it.close()
        }
    }

    fun getPlayerTPEHistory(playerId: String): List<Pair<String, Int>> {

        val tpeHistoryList = ArrayList<Pair<String, Int>>()
        tpeHistoryList.add(Pair("", 50))

        getConnection().let {
            val ruleSet = it.createStatement().executeQuery("SELECT * FROM players WHERE player_id='$playerId'")
            it.close()
            while (ruleSet.next()) {
                tpeHistoryList.add(
                        Pair(ruleSet.getString("date").substring(0, 5), ruleSet.getInt("tpe"))
                )
            }
        }

        return tpeHistoryList
    }

    private fun getConnection() = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))
}