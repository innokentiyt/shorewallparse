package org.innokentiyt.shorewallparser.utils

import org.innokentiyt.shorewallparser.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class ShWallParseHelper {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Application::class.java)

        fun parseConfToList(confFile: File): List<ShWallParsedStringData> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun createConfFromList(confDataList: List<ShWallParsedStringData>, dbName: String, directory: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}