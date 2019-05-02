package org.innokentiyt.shorewallparser.utils

import org.innokentiyt.shorewallparser.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.lang.RuntimeException

class ShWallParseHelper {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Application::class.java)

        fun parseConfToList(confFile: File): List<ShWallParsedStringData> {
            val tempList: List<ShWallParsedStringData> = emptyList()
            confFile.forEachLine {
                if(it.isNotEmpty())
                    it.split(" ").let { line ->
                        val word1 = line[1].split(":")
                        val word2 = line[2].split(":")
                        val source: Map<String, String?> = when {
                            word1.size == 2 -> mapOf(word1[0] to word1[1])
                            else -> throw RuntimeException("Currently supports sources only like \"loc:\$username\". Exiting...")
                        }
                        val dest: Map<String, List<String>?> = when {
                            word2.size == 2 -> mapOf(word2[0] to word2[1].split(",").sorted())
                            word2.size == 1 -> mapOf(word2[0] to null)
                            else -> throw RuntimeException("Destinations part of the config has incorrect format. Exiting...")
                        }
                        tempList.plus(
                            ShWallParsedStringData(
                            action = line[0],
                            source = source,
                            dest = dest,
                            proto = line[3],
                            ports = if (line.size == 5) line[4].split(",") else null
                        ))
                    }
            }
            return tempList
        }

        fun createConfFromList(confDataList: List<ShWallParsedStringData>, dbName: String, directory: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}