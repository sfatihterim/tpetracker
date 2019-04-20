package com.nsfl.tpetracker.model.player

import com.nsfl.tpetracker.model.position.Position
import com.nsfl.tpetracker.model.team.Team

class Player(
        val id: String,
        val name: String,
        val team: Team,
        val position: Position,
        val draftYear: String,
        val tpe: Int
)