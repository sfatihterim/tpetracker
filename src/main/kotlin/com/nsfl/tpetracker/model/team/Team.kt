package com.nsfl.tpetracker.model.team

enum class Team(
        val id: String,
        val url: String,
        val full: String,
        val type: Type
) {
    BALTIMORE_HAWKS("69", "/baltimore_hawks", "Baltimore Hawks", Type.NSFL),
    CHICAGO_BUTCHERS("324", "/chicago_butchers", "Chicago Butchers", Type.NSFL),
    COLORADO_YETI("55", "/colorado_yeti", "Colorado Yeti", Type.NSFL),
    PHILADELPHIA_LIBERTY("110", "/philadelphia_liberty", "Philadelphia Liberty", Type.NSFL),
    SARASOTA_SAILFISH("369", "/sarasota_sailfish", "Sarasota Sailfish", Type.NSFL),
    YELLOWKNIFE_WRAITHS("57", "/yellowknife_wraiths", "Yellowknife Wraiths", Type.NSFL),
    ARIZONA_OUTLAWS("72", "/arizona_outlaws", "Arizona Outlaws", Type.NSFL),
    AUSTIN_COPPERHEADS("327", "/austin_copperheads", "Austin Copperheads", Type.NSFL),
    HONOLULU_HAHALUA("371", "/honolulu_hahalua", "Honolulu Hahalua", Type.NSFL),
    NEW_ORLEANS_SECOND_LINE("113", "/new_orleans_second_line", "New Orleans Second Line", Type.NSFL),
    ORANGE_COUNTY_OTTERS("53", "/orange_county_otters", "Orange County Otters", Type.NSFL),
    SAN_JOSE_SABERCATS("51", "/san_jose_sabercats", "San Jose SaberCats", Type.NSFL),
    MYRTLE_BEACH_BUCCANEERS("160", "/myrtle_beach_buccaneers", "Myrtle Beach Buccaneers", Type.DSFL),
    KANSAS_CITY_COYOTES("158", "/kansas_city_coyotes", "Kansas City Coyotes", Type.DSFL),
    PORTLAND_PYTHONS("164", "/portland_pythons", "Portland Pythons", Type.DSFL),
    NORFOLK_SEAWOLVES("162", "/norfolk_seawolves", "Norfolk SeaWolves", Type.DSFL),
    MINNESOTA_GREY_DUCKS("156", "/minnesota_grey_ducks", "Minnesota Grey Ducks", Type.DSFL),
    TIJUANA_LUCHADORES("154", "/tijuana_luchadores", "Tijuana Luchadores", Type.DSFL),
    DALLAS_BIRDDOGS("356", "/dallas_birddogs", "Dallas Birddogs", Type.DSFL),
    LONDON_ROYALS("353", "/london_royals", "London Royals", Type.DSFL),
    FREE_AGENTS("34", "/free_agents", "Free Agents", Type.FREE_AGENT),
    QB_PROSPECTS("84", "/qb_prospects", "QB Prospects", Type.PROSPECT),
    RB_PROSPECTS("85", "/rb_prospects", "RB Prospects", Type.PROSPECT),
    WR_PROSPECTS("86", "/wr_prospects", "WR Prospects", Type.PROSPECT),
    TE_PROSPECTS("87", "/te_prospects", "TE Prospects", Type.PROSPECT),
    OL_PROSPECTS("88", "/ol_prospects", "OL Prospects", Type.PROSPECT),
    DE_PROSPECTS("89", "/de_prospects", "DE Prospects", Type.PROSPECT),
    DT_PROSPECTS("90", "/dt_prospects", "DT Prospects", Type.PROSPECT),
    LB_PROSPECTS("91", "/lb_prospects", "LB Prospects", Type.PROSPECT),
    CB_PROSPECTS("92", "/cb_prospects", "CB Prospects", Type.PROSPECT),
    S_PROSPECTS("93", "/s_prospects", "S Prospects", Type.PROSPECT),
    KP_PROSPECTS("94", "/kp_prospects", "K/P Prospects", Type.PROSPECT);

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
