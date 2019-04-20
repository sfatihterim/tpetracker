package com.nsfl.tpetracker.model

class Player(
        val id: String,
        val name: String,
        val team: Team,
        val position: Position,
        val draftYear: String,
        val tpe: Int
)