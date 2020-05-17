package com.nsfl.tpetracker.html

import com.beust.klaxon.Json
import com.beust.klaxon.JsonArray
import com.beust.klaxon.KlaxonJson
import com.beust.klaxon.json
import kotlinx.html.*

fun DIV.tablePage(name: String, script: String?, jsonGenerator: KlaxonJson.() -> JsonArray<Any?>) {

    div {
        // this should really be classed and put in src/resources/main.css
        style = "padding-left:80px;padding-right:80px;padding-top:40px;padding-bottom:20px;color:rgba(0,0,0,.87);font-size:28px;text-align:center"
        +name
    }

    div {
        // this should really be classed and put in src/resources/main.css
        style = "padding-left:80px;padding-right:80px;padding-top:20px;padding-bottom:40px"
        table(classes = "celled table ui") {
            id = "table"
            attributes["width"] = "100%%"
            caption {}
        }
    }

    script {
        unsafe {
            +"var dataSet = "
            +json(init = jsonGenerator).toJsonString()
        }
    }

    if (script != null)
        script(src = "/static/js/$script") {}

}
