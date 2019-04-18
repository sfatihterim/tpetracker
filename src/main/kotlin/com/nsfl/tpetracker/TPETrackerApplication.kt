package com.nsfl.tpetracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.jsoup.Jsoup

@RestController
@SpringBootApplication
class TpeTrackerApplication {

    @RequestMapping
    fun test(): String {
        val doc = Jsoup.connect("http://en.wikipedia.org/").get()
        return doc.title()
    }
}

fun main(args: Array<String>) {
    runApplication<TpeTrackerApplication>(*args)
}