package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team

class ActivePlayer(
        id: String,
        user: String,
        name: String,
        team: Team,
        position: Position,
        draftYear: String,
        currentTPE: Int,
        highestTPE: Int,
        lastUpdated: String,
        tpeHistoryList: List<Pair<String, Int>>,
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
        val kickAccuracy: Int,
        val lastSeen: String,
        val height: Int,
        val weight: Int
) : Player(id, user, name, team, position, draftYear, currentTPE, highestTPE, lastUpdated, tpeHistoryList)