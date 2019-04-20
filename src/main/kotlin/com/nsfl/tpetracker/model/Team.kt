package com.nsfl.tpetracker.model

enum class Team(
        val id: String,
        val url: String,
        val full: String
) {
    BALTIMORE_HAWKS("69", "/baltimore_hawks", "Baltimore Hawks"),
    COLORADO_YETI("55", "/colorado_yeti", "Colorado Yeti"),
    PHILADELPHIA_LIBERTY("110", "/philadelphia_liberty", "Philadelphia Liberty"),
    YELLOWKNIFE_WRAITHS("57", "/yellowknife_wraiths", "Yellowknife Wraiths"),
    ARIZONA_OUTLAWS("72", "/arizona_outlaws", "Arizona Outlaws"),
    NEW_ORLEANS_SECOND_LINE("113", "/new_orleans_second_line", "New Orleans Second Line"),
    ORANGE_COUNTY_OTTERS("53", "/orange_county_otters", "Orange County Otters"),
    SAN_JOSE_SABERCATS("51", "/san_jose_sabercats", "San Jose SaberCats"),
    PALM_BEACH_SOLAR_BEARS("160", "/palm_beach_solar_bears", "Palm Beach Solar Bears"),
    KANSAS_CITY_COYOTES("158", "/kansas_city_coyotes", "Kansas City Coyotes"),
    PORTLAND_PYTHONS("164", "/portland_pythons", "Portland Pythons"),
    NORFOLK_SEAWOLVES("162", "/norfolk_seawolves", "Norfolk SeaWolves"),
    SAN_ANTONIO_MARSHALS("156", "/san_antonio_marshals", "San Antonio Marshals"),
    TIJUANA_LUCHADORES("154", "/tijuana_luchadores", "Tijuana Luchadores"),
    FREE_AGENTS("34", "/free_agents", "Free Agents"),
    QB_PROSPECTS("84", "/qb_prospects", "QB Prospects"),
    RB_PROSPECTS("85", "/rb_prospects", "RB Prospects"),
    WR_PROSPECTS("86", "/wr_prospects", "WR Prospects"),
    TE_PROSPECTS("87", "/te_prospects", "TE Prospects"),
    OL_PROSPECTS("88", "/ol_prospects", "OL Prospects"),
    DE_PROSPECTS("89", "/de_prospects", "DE Prospects"),
    DT_PROSPECTS("90", "/dt_prospects", "DT Prospects"),
    LB_PROSPECTS("91", "/lb_prospects", "LB Prospects"),
    CB_PROSPECTS("92", "/cb_prospects", "CB Prospects"),
    S_PROSPECTS("93", "/s_prospects", "S Prospects"),
    KP_PROSPECTS("94", "/kp_prospects", "K/P Prospects")
}