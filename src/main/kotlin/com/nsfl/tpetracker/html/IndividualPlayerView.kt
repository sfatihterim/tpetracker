package com.nsfl.tpetracker.html

import com.beust.klaxon.json
import com.nsfl.tpetracker.model.player.Player
import kotlinx.html.*

fun DIV.individualPlayerView(player: Player) {

    container {
        div {
            br {}
            h2 { a(href = "http://nsfl.jcink.net/index.php?showtopic=${player.id}") { +player.name } }
            h5 { a(href = player.team.url) { +player.team.full } }
            +"${player.position.full} - ${player.draftYear}"
            br {}
        }
        div {
            canvas { id = "myChart" }
        }
    }

    script {
        unsafe {
            +"var labelSet = "
            +json {
                array(
                    player.tpeHistoryList.map {
                        if (it.first.isEmpty()) {
                            "'${it.first}'"
                        } else {
                            "'${it.first.substring(0, 5)}'"
                        }
                    }
                )
            }.toJsonString()
            +"; var dataSet = "
            +json {
                array(
                        player.tpeHistoryList.map { it.second }
                )
            }.toJsonString()
        }
    }

    script(src = "/static/js/individual-player-chart-generator.js") {}

}
