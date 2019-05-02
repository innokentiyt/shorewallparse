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
            val tempList: MutableList<ShWallParsedStringData> = mutableListOf()
            confFile.forEachLine {
                if(it.isNotEmpty())
                    it.split(" ").let { line ->
                        val word1 = line[1].split(":")
                        val word2 = line[2].split(":")
                        val source: Pair<String, String?> = when {
                            word1.size == 2 -> Pair(word1[0], word1[1].replace("$",""))
                            else -> throw RuntimeException("Currently supports sources only like \"loc:\$username\". Exiting...")
                        }
                        val dest: Pair<String, List<String>?> = when {
                            word2.size == 2 -> Pair(word2[0], word2[1].split(",").sorted())
                            word2.size == 1 -> Pair(word2[0], null)
                            else -> throw RuntimeException("Destinations part of the config has incorrect format. Exiting...")
                        }
                        tempList.add(
                            ShWallParsedStringData(
                            action = line[0],
                            source = source,
                            dest = dest,
                            proto = line[3],
                            ports = if ((line.size == 5) and line[4].isNotEmpty()) line[4].split(",").sorted() else null
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