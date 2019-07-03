package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team

class RetiredPlayer(
        id: String,
        user: String,
        name: String,
        team: Team,
        position: Position,
        draftYear: String,
        currentTPE: Int,
        highestTPE: Int,
        lastUpdated: String,
        tpeHistoryList: List<Pair<String, Int>>
) : Player(id, user, name, team, position, draftYear, currentTPE, highestTPE, lastUpdated, tpeHistoryList)