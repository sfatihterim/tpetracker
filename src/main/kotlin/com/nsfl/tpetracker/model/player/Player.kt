package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team

class Player(
        val id: String,
        val user: String,
        val name: String,
        val team: Team,
        val position: Position,
        val draftYear: String,
        val currentTPE: Int,
        val highestTPE: Int,
        val lastUpdated: String,
        val tpeHistoryList: List<Pair<String, Int>>,
        val strength: Int,
        val agility: Int,
        val arm: Int,
        val intelligence: Int,
        val throwingAccuracy: Int,
        val tackling: Int,
        val speed: Int,
        val hands: Int,
        val passBlocking: Int,
        val runBlocking: Int,
        val endurance: Int,
        val kickPower: Int,
        val kickAccuracy: Int
)