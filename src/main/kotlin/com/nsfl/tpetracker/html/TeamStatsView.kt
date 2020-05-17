package com.nsfl.tpetracker.html

import com.beust.klaxon.json
import com.nsfl.tpetracker.model.player.ActivePlayer
import com.nsfl.tpetracker.model.team.Team
import kotlinx.html.*

fun DIV.teamStatsView(nsflList: List<Pair<Team, List<ActivePlayer>>>, dsflList: List<Pair<Team, List<ActivePlayer>>>) = tablePage("Team Stats", "team-stats-table-generator.js") {

    array(nsflList.map { pair ->
        array(
                "NSFL",
                "<a href=\"${pair.first.url}\">${pair.first.full}</a>",
                pair.second.getTotalTPE(),
                pair.second.getAverageTPE(),
                pair.second.getTotalTPE(),
                pair.second.getAverageTPE(),
                pair.second.getOffensiveTPE(false),
                pair.second.getDefensiveTPE(false)
        )
    } + dsflList.map { pair ->
        array(
                "DSFL",
                "<a href=\"${pair.first.url}\">${pair.first.full}</a>",
                pair.second.getTotalTPE(),
                pair.second.getAverageTPE(),
                pair.second.getTotalEffectiveTPE(),
                pair.second.getAverageEffectiveTPE(),
                pair.second.getOffensiveTPE(false),
                pair.second.getDefensiveTPE(false)
        )
    })

}
