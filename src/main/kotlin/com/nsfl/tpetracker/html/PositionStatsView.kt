package com.nsfl.tpetracker.html

import com.nsfl.tpetracker.model.player.ActivePlayer
import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team
import kotlinx.html.DIV

fun DIV.positionStatsView(positionList: List<Pair<Position, List<ActivePlayer>>>) = tablePage("Position Stats", "position-stats-table-generator.js") {
    array(positionList.map { pair ->
        val nsflList = pair.second.filter { it.team.type == Team.Type.NSFL }
        val dsflList = pair.second.filter { it.team.type == Team.Type.DSFL }
        val freeAgentList = pair.second.filter { it.team.type == Team.Type.FREE_AGENT }
        val prospectList = pair.second.filter { it.team.type == Team.Type.PROSPECT }
        array(
                pair.first.full,
                nsflList.size,
                dsflList.size,
                freeAgentList.size,
                prospectList.size,
                pair.second.size,
                nsflList.minBy(0) { it.currentTPE },
                nsflList.maxBy(0) { it.currentTPE },
                nsflList.sumBy { it.currentTPE } / nsflList.countOrOne(),
                dsflList.minBy(0) { it.currentTPE },
                dsflList.maxBy(0) { it.currentTPE },
                dsflList.sumBy { it.currentTPE } / dsflList.countOrOne()
        )
    })
}
