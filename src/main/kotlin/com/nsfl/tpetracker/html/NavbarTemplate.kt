package com.nsfl.tpetracker.html

import kotlinx.html.*

fun HTML.navbarTemplate(title: String, contents: DIV.() -> Unit) {
    head {
        title { +"TPE Tracker - $title" }
        link(href = "https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css", rel = "stylesheet")
        link(href = "https://cdn.datatables.net/buttons/1.5.6/css/buttons.bootstrap4.min.css", rel = "stylesheet")
        link(href = "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css", rel = "stylesheet")

        script(src = "https://code.jquery.com/jquery-3.3.1.js") {}
        script(src = "https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js") {}
        script(src = "https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js") {}
        script(src = "https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js") {}
        script(src = "https://cdn.datatables.net/buttons/1.6.1/js/buttons.flash.min.js") {}
        script(src = "https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js") {}
        script(src = "https://cdn.datatables.net/buttons/1.5.6/js/buttons.bootstrap4.min.js") {}
        script(src = "https://cdn.datatables.net/buttons/1.5.6/js/buttons.colVis.min.js") {}
        script(src = "https://cdn.datatables.net/plug-ins/1.10.19/sorting/enum.js") {}
        script(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.bundle.min.js") {}

        link(href = "/static/css/main.css", rel = "stylesheet")
    }

    body {

        nav(classes = "bg-light navbar navbar-expand-lg navbar-light rounded") {
            button(classes = "collapsed navbar-toggler", type = ButtonType.button) {
                attributes["aria-controls"] = "navbar"
                attributes["aria-expanded"] = "false"
                attributes["aria-label"] = "Toggle navigation"
                attributes["data-target"] = "#navbar"
                attributes["data-toggle"] = "collapse"
                span(classes = "navbar-toggler-icon") {}
            }

            div(classes = "collapse navbar-collapse") {
                id = "navbar"
                ul(classes = "mr-auto navbar-nav") {

                    navItem(href = "/", active = true) { +"Home" }
                    navItem(href = "/all_players") { +"All Players" }
                    navItem(href = "/team_stats") { +"Team Stats" }
                    navItem(href = "/position_stats") { +"Position Stats" }
                    navItem(href = "/activity_check_query") { +"Activity Check" }

                    navItemDropdown(name = "NSFL") {
                        dropdownItem(href = "/baltimore_hawks") { +"Baltimore Hawks" }
                        dropdownItem(href = "/chicago_butchers") { +"Chicago Butchers" }
                        dropdownItem(href = "/colorado_yeti") { +"Colorado Yeti" }
                        dropdownItem(href = "/philadelphia_liberty") { +"Philadelphia Liberty" }
                        dropdownItem(href = "/yellowknife_wraiths") { +"Yellowknife Wraiths" }
                        dropdownItem(href = "/arizona_outlaws") { +"Arizona Outlaws" }
                        dropdownItem(href = "/austin_copperheads") { +"Austin Copperheads" }
                        dropdownItem(href = "/new_orleans_second_line") { +"New Orleans Second Line" }
                        dropdownItem(href = "/orange_county_otters") { +"Orange County Otters" }
                        dropdownItem(href = "/san_jose_sabercats") { +"San Jose SaberCats" }
                    }

                    navItemDropdown(name = "DSFL") {
                        dropdownItem(href = "/myrtle_beach_buccaneers") { +"Myrtle Beach Buccaneers" }
                        dropdownItem(href = "/london_royals") { +"London Royals" }
                        dropdownItem(href = "/dallas_birddogs") { +"Dallas Birddogs" }
                        dropdownItem(href = "/kansas_city_coyotes") { +"Kansas City Coyotes" }
                        dropdownItem(href = "/portland_pythons") { +"Portland Pythons" }
                        dropdownItem(href = "/norfolk_seawolves") { +"Norfolk SeaWolves" }
                        dropdownItem(href = "/minnesota_grey_ducks") { +"Minnesota Grey Ducks" }
                        dropdownItem(href = "/tijuana_luchadores") { +"Tijuana Luchadores" }
                    }

                    navItem(href = "/free_agents") { +"Free Agents" }

                    navItemDropdown(name = "Prospects") {
                        dropdownItem(href = "/qb_prospects") { +"QB Prospects" }
                        dropdownItem(href = "/rb_prospects") { +"RB Prospects" }
                        dropdownItem(href = "/wr_prospects") { +"WR Prospects" }
                        dropdownItem(href = "/te_prospects") { +"TE Prospects" }
                        dropdownItem(href = "/ol_prospects") { +"OL Prospects" }
                        dropdownItem(href = "/de_prospects") { +"DE Prospects" }
                        dropdownItem(href = "/dt_prospects") { +"DT Prospects" }
                        dropdownItem(href = "/lb_prospects") { +"LB Prospects" }
                        dropdownItem(href = "/cb_prospects") { +"CB Prospects" }
                        dropdownItem(href = "/s_prospects") { +"S Prospects" }
                        dropdownItem(href = "/kp_prospects") { +"K/P Prospects" }
                    }

                    navItem(href = "/retired_players") { +"Retired Players" }
                }
            }
        }

        div(block = contents)

    }

}
