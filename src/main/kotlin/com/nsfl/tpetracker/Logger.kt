package com.nsfl.tpetracker

import org.slf4j.LoggerFactory

object Logger {

    private val logger = LoggerFactory.getLogger("TPETrackerApplication")

    fun info(info: String) {
        logger.info(info)
    }

    fun error(error: String) {
        logger.error(error)
    }
}