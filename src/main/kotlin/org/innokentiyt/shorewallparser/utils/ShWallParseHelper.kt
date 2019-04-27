package org.innokentiyt.shorewallparser.utils

import org.innokentiyt.shorewallparser.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class ShWallParseHelper {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Application::class.java)

        fun parseToList(confFile: File): List<ShWallParsedStringData> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun createDbFromList(confDataList: List<ShWallParsedStringData>, name: String, directory: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}