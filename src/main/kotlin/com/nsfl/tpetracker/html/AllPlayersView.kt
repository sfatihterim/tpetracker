package com.nsfl.tpetracker.html

import com.beust.klaxon.json
import com.nsfl.tpetracker.model.player.ActivePlayer
import kotlinx.html.*

fun DIV.allPlayersView(allPlayers: List<ActivePlayer>) {

    div {
        // this should really be classed and put in src/resources/main.css
        style = "padding-left:80px;padding-right:80px;padding-top:40px;padding-bottom:20px;color:rgba(0,0,0,.87);font-size:28px;text-align:center"
        +"All Players"
    }

    div {
        // this should really be classed and put in src/resources/main.css
        style = "padding-left:80px;padding-right:80px;padding-top:20px;padding-bottom:40px"
        table(classes = "celled table ui") {
            id = "table"
            attributes["width"] = "100%%"
            caption {}
        }
    }

    script {
        unsafe {
            +"var dataSet = "
            +json {
                array(allPlayers.map {
                    array(
                            "<a href=\"http://nsfl.jcink.net/index.php?showtopic=${it.id}\">${it.user}</a>",
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
                            it.kickAccuracy
                    )
                })
            }.toJsonString()
        }
    }

    script(src = "/static/js/all-players-table-generator.js") {}
}
