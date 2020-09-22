package com.nsfl.tpetracker.model.team

enum class Team(
        val id: String,
        val url: String,
        val full: String,
        val type: Type
) {
    BALTIMORE_HAWKS("82", "/baltimore_hawks", "Baltimore Hawks", Type.NSFL),
    BERLIN_FIRE_SALAMANDERS("368","/berlin_fire_salamanders","Berlin Fire Salamanders", Type.NSFL),
    CHICAGO_BUTCHERS("160", "/chicago_butchers", "Chicago Butchers", Type.NSFL),
    COLORADO_YETI("66", "/colorado_yeti", "Colorado Yeti", Type.NSFL),
    PHILADELPHIA_LIBERTY("144", "/philadelphia_liberty", "Philadelphia Liberty", Type.NSFL),
    SARASOTA_SAILFISH("306", "/sarasota_sailfish", "Sarasota Sailfish", Type.NSFL),
    YELLOWKNIFE_WRAITHS("68", "/yellowknife_wraiths", "Yellowknife Wraiths", Type.NSFL),
    ARIZONA_OUTLAWS("85", "/arizona_outlaws", "Arizona Outlaws", Type.NSFL),
    AUSTIN_COPPERHEADS("229", "/austin_copperheads", "Austin Copperheads", Type.NSFL),
    HONOLULU_HAHALUA("308", "/honolulu_hahalua", "Honolulu Hahalua", Type.NSFL),
    NEW_ORLEANS_SECOND_LINE("147", "/new_orleans_second_line", "New Orleans Second Line", Type.NSFL),
    NEW_YORK_SILVERBACKS("371","/new_york_silverbacks","New York Silverbacks", Type.NSFL),
    ORANGE_COUNTY_OTTERS("64", "/orange_county_otters", "Orange County Otters", Type.NSFL),
    SAN_JOSE_SABERCATS("62", "/san_jose_sabercats", "San Jose SaberCats", Type.NSFL),
    MYRTLE_BEACH_BUCCANEERS("195", "/myrtle_beach_buccaneers", "Myrtle Beach Buccaneers", Type.DSFL),
    KANSAS_CITY_COYOTES("193", "/kansas_city_coyotes", "Kansas City Coyotes", Type.DSFL),
    PORTLAND_PYTHONS("199", "/portland_pythons", "Portland Pythons", Type.DSFL),
    NORFOLK_SEAWOLVES("197", "/norfolk_seawolves", "Norfolk SeaWolves", Type.DSFL),
    MINNESOTA_GREY_DUCKS("191", "/minnesota_grey_ducks", "Minnesota Grey Ducks", Type.DSFL),
    TIJUANA_LUCHADORES("189", "/tijuana_luchadores", "Tijuana Luchadores", Type.DSFL),
    DALLAS_BIRDDOGS("297", "/dallas_birddogs", "Dallas Birddogs", Type.DSFL),
    LONDON_ROYALS("234", "/london_royals", "London Royals", Type.DSFL),
    FREE_AGENTS("48", "/free_agents", "Free Agents", Type.FREE_AGENT),
    QB_PROSPECTS("110", "/qb_prospects", "QB Prospects", Type.PROSPECT),
    RB_PROSPECTS("112", "/rb_prospects", "RB Prospects", Type.PROSPECT),
    WR_PROSPECTS("113", "/wr_prospects", "WR Prospects", Type.PROSPECT),
    TE_PROSPECTS("114", "/te_prospects", "TE Prospects", Type.PROSPECT),
    OL_PROSPECTS("115", "/ol_prospects", "OL Prospects", Type.PROSPECT),
    DE_PROSPECTS("116", "/de_prospects", "DE Prospects", Type.PROSPECT),
    DT_PROSPECTS("117", "/dt_prospects", "DT Prospects", Type.PROSPECT),
    LB_PROSPECTS("118", "/lb_prospects", "LB Prospects", Type.PROSPECT),
    CB_PROSPECTS("119", "/cb_prospects", "CB Prospects", Type.PROSPECT),
    S_PROSPECTS("120", "/s_prospects", "S Prospects", Type.PROSPECT),
    KP_PROSPECTS("121", "/kp_prospects", "K/P Prospects", Type.PROSPECT);

    enum class Type {
        NSFL, DSFL, FREE_AGENT, PROSPECT
    }

    companion object {
        fun fromName(name: String): Team {
            return when (name) {
                "PALM_BEACH_SOLAR_BEARS" -> MYRTLE_BEACH_BUCCANEERS
                "SAN_ANTONIO_MARSHALS" -> MINNESOTA_GREY_DUCKS
                else -> Team.valueOf(name)
            }
        }
    }
}
