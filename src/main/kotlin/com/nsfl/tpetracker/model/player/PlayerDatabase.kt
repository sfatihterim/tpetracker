package com.nsfl.tpetracker.model.player

import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.*

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

    private fun getConnection() = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))
}