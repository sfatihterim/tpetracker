package com.nsfl.tpetracker.model.team

enum class Team(
        val id: String,
        val url: String,
        val full: String,
        val type: Type
) {
    BALTIMORE_HAWKS("69", "/baltimore_hawks", "Baltimore Hawks", Type.NSFL),
    COLORADO_YETI("55", "/colorado_yeti", "Colorado Yeti", Type.NSFL),
    PHILADELPHIA_LIBERTY("110", "/philadelphia_liberty", "Philadelphia Liberty", Type.NSFL),
    YELLOWKNIFE_WRAITHS("57", "/yellowknife_wraiths", "Yellowknife Wraiths", Type.NSFL),
    ARIZONA_OUTLAWS("72", "/arizona_outlaws", "Arizona Outlaws", Type.NSFL),
    NEW_ORLEANS_SECOND_LINE("113", "/new_orleans_second_line", "New Orleans Second Line", Type.NSFL),
    ORANGE_COUNTY_OTTERS("53", "/orange_county_otters", "Orange County Otters", Type.NSFL),
    SAN_JOSE_SABERCATS("51", "/san_jose_sabercats", "San Jose SaberCats", Type.NSFL),
    PALM_BEACH_SOLAR_BEARS("160", "/palm_beach_solar_bears", "Palm Beach Solar Bears", Type.DSFL),
    KANSAS_CITY_COYOTES("158", "/kansas_city_coyotes", "Kansas City Coyotes", Type.DSFL),
    PORTLAND_PYTHONS("164", "/portland_pythons", "Portland Pythons", Type.DSFL),
    NORFOLK_SEAWOLVES("162", "/norfolk_seawolves", "Norfolk SeaWolves", Type.DSFL),
    SAN_ANTONIO_MARSHALS("156", "/san_antonio_marshals", "San Antonio Marshals", Type.DSFL),
    TIJUANA_LUCHADORES("154", "/tijuana_luchadores", "Tijuana Luchadores", Type.DSFL),
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
}