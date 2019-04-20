package com.nsfl.tpetracker.model.position

enum class Position(
        val abbreviation: String,
        val full: String
) {
    QB("QB", "Quarterback"),
    RB("RB", "Running Back"),
    WR("WR", "Wide Receiver"),
    TE("TE", "Tight End"),
    OL("OL", "Offensive Line"),
    DE("DE", "Defensive End"),
    DT("DT", "Defensive Tackle"),
    LB("LB", "Linebacker"),
    CB("CB", "Cornerback"),
    S("S", "Safety"),
    KP("K/P", "Kicker/Punter"),
    UNKNOWN("", "Unknown");

    companion object {
        fun fromAbbreviation(abbreviation: String) =
                values().firstOrNull { it.abbreviation == abbreviation } ?: UNKNOWN
    }
}