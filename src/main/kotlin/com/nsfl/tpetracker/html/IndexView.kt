package com.nsfl.tpetracker.html

import kotlinx.html.*

private fun DIV.pageItem(url: String, name: String, wide: Boolean = false, imgSrc: String? = null, xlMargin: Int = if (wide) 6 else 4) {
    a(classes = "col-md-6 mb-4 col-xl-${xlMargin}", href = url) {
        card(classes = "border-0 shadow") {
            if (imgSrc != null) {
                cardImgTop(src = imgSrc) {}
            }
            cardBody(classes = "text-center") {
                if (wide)
                    br {}
                cardTitle(classes = "mb-0") { +name }
                if (wide)
                    br {}
            }
        }
    }
}

fun HTML.indexView() {
    head {
        title { +"TPE Tracker" }
        link(href = "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css", rel = "stylesheet") {}
        script(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.bundle.min.js") {}
    }
    body {

        // main pages
        br {}
        br {}
        container {
            row {
                pageItem(url = "/all_players", name = "All Players", wide = true)
                pageItem(url = "/team_stats", name = "Team Stats", wide = true)
                pageItem(url = "/position_stats", name = "Position Stats", wide = true)
                pageItem(url = "/activity_check", name = "Activity Check", wide = true)
            }
        }

        // nsfl team pages
        br {}
        container {
            row {
                pageItem(url = "/baltimore_hawks", name = "Baltimore Hawks", imgSrc = "https://i.imgur.com/V7b1IrD.png")
                pageItem(url = "/chicago_butchers", name = "Chicago Butchers", imgSrc = "https://cdn.discordapp.com/attachments/480923375913664524/588935077392547840/CHIBanner.png")
                pageItem(url = "/colorado_yeti", name = "Colorado Yeti", imgSrc = "https://i.imgur.com/F82SkOe.png")
                pageItem(url = "/philadelphia_liberty", name = "Philadelphia Liberty", imgSrc = "https://i.imgur.com/e26kJIj.png")
                pageItem(url = "/yellowknife_wraiths", name = "Yellowknife Wraiths", imgSrc = "https://i.imgur.com/HTNIYcS.png")
                pageItem(url = "/arizona_outlaws", name = "Arizona Outlaws", imgSrc = "https://i.imgur.com/D2Cu5bS.png")
                pageItem(url = "/austin_copperheads", name = "Austin Copperheads", imgSrc = "https://cdn.discordapp.com/attachments/480923375913664524/588936405548400641/AUSBanner.png")
                pageItem(url = "/new_orleans_second_line", name = "New Orleans Second Line", imgSrc = "https://i.imgur.com/39Pv6j6.png")
                pageItem(url = "/orange_county_otters", name = "Orange County Otters", imgSrc = "https://i.imgur.com/A1jDLTx.png")
                pageItem(url = "/san_jose_sabercats", name = "San Jose SaberCats", imgSrc = "https://i.imgur.com/WKAzCvY.png")
            }
        }

        // dsfl team pages
        br {}
        container {
            row {
                pageItem(url = "/myrtle_beach_buccaneers", name = "Myrtle Beach Buccaneers", imgSrc = "https://cdn.discordapp.com/attachments/589456641447952387/634231640155226123/MBbanner.png")
                pageItem(url = "/london_royals", name = "London Royals", imgSrc = "https://i.imgur.com/FG0PXqA.png")
                pageItem(url = "/dallas_birddogs", name = "Dallas Birddogs", imgSrc = "https://i.imgur.com/MtLEciQ.png")
                pageItem(url = "/kansas_city_coyotes", name = "Kansas City Coyotes", imgSrc = "https://media.discordapp.net/attachments/574036391038812180/593205480038596658/KCCBanner.png")
                pageItem(url = "/portland_pythons", name = "Portland Pythons", imgSrc = "https://i.imgur.com/g1jBrkG.png")
                pageItem(url = "/norfolk_seawolves", name = "Norfolk SeaWolves", imgSrc = "https://i.imgur.com/rUbmsUh.png")
                pageItem(url = "/minnesota_grey_ducks", name = "Minnesota Grey Ducks", imgSrc = "https://cdn.discordapp.com/attachments/650514725192466443/651621525840986122/MINBanner1_blue.png")
                pageItem(url = "/tijuana_luchadores", name = "Tijuana Luchadores", imgSrc = "https://i.imgur.com/Rqa9DsX.png")
            }
        }

        // player category pages
        br {}
        container {
            row {
                pageItem(url = "/free_agents", name = "Free Agents")
                pageItem(url = "/qb_prospects", name = "QB Prospects")
                pageItem(url = "/rb_prospects", name = "RB Prospects")
                pageItem(url = "/wr_prospects", name = "WR Prospects")
                pageItem(url = "/te_prospects", name = "TE Prospects")
                pageItem(url = "/ol_prospects", name = "OL Prospects")
                pageItem(url = "/de_prospects", name = "DE Prospects")
                pageItem(url = "/dt_prospects", name = "DT Prospects")
                pageItem(url = "/lb_prospects", name = "LB Prospects")
                pageItem(url = "/cb_prospects", name = "CB Prospects")
                pageItem(url = "/s_prospects", name = "S Prospects")
                pageItem(url = "/kp_prospects", name = "K/P Prospects")
                pageItem(url = "/retired_players", name = "Retired Players")
            }
        }

        br {}
        br {}
    }
}
