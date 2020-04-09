package com.nsfl.tpetracker.html

import com.beust.klaxon.json
import com.nsfl.tpetracker.model.player.ActivePlayer
import com.nsfl.tpetracker.model.team.Team
import kotlinx.html.*

fun DIV.teamStatsView(nsflList: List<Pair<Team, List<ActivePlayer>>>, dsflList: List<Pair<Team, List<ActivePlayer>>>) {

    div {
        // this should really be classed and put in src/resources/main.css
        style = "padding-left:80px;padding-right:80px;padding-top:40px;padding-bottom:20px;color:rgba(0,0,0,.87);font-size:28px;text-align:center"
        +"Team Stats"
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
                array(nsflList.map { pair ->
                    array(
                            "NSFL",
                            "<a href=\"${pair.first.url}\">${pair.first.full}</a>",
                            "${pair.second.getTotalTPE()}",
                            "${pair.second.getAverageTPE()}",
                            "${pair.second.getTotalTPE()}",
                            "${pair.second.getAverageTPE()}",
                            "${pair.second.getOffensiveTPE(false)}",
                            "${pair.second.getDefensiveTPE(false)}"
                    )
                } + dsflList.map { pair ->
                    array(
                            "DSFL",
                            "<a href=\"${pair.first.url}\">${pair.first.full}</a>",
                            "${pair.second.getTotalTPE()}",
                            "${pair.second.getAverageTPE()}",
                            "${pair.second.getTotalTPE()}",
                            "${pair.second.getAverageTPE()}",
                            "${pair.second.getOffensiveTPE(true)}",
                            "${pair.second.getDefensiveTPE(true)}"
                    )
                })
            }.toJsonString()
        }
    }

    script(src = "/static/js/team-stats-table-generator.js") {}

}
