package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team

abstract class Player(
        val id: String,
        val user: String,
        val name: String,
        val team: Team,
        val position: Position,
        val draftYear: String,
        val currentTPE: Int,
        val highestTPE: Int,
        val lastUpdated: String,
        val tpeHistoryList: List<Pair<String, Int>>
)