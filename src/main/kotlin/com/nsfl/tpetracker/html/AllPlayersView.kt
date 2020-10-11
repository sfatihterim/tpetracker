package com.nsfl.tpetracker.html

import com.beust.klaxon.json
import com.nsfl.tpetracker.model.player.ActivePlayer
import kotlinx.html.*

fun DIV.allPlayersView(allPlayers: List<ActivePlayer>) = tablePage("All Players", "all-players-table-generator.js") {

    array(
            allPlayers.map {
                array(
                        "<a href=\"https://forums.sim-football.com/showthread.php?tid=${it.id}\">${it.user}</a>",
                        it.draftYear,
                        "<a href=\"${it.team.url}\">${it.team.full}</a>",
                        "<a href=\"/player?playerId=${it.id}\">${it.name}</a>",
                        it.position.full,
                        it.currentTPE,
                        it.highestTPE,
                        it.lastUpdated,
                        it.lastSeen,
                        it.strength,
                        it.agility,
                        it.arm,
                        it.intelligence,
                        it.throwingAccuracy,
                        it.tackling,
                        it.speed,
                        it.hands,
                        it.passBlocking,
                        it.runBlocking,
                        it.endurance,
                        it.kickPower,
                        it.kickAccuracy,
                        it.height,
                        it.weight
                )
            }
    )

}
