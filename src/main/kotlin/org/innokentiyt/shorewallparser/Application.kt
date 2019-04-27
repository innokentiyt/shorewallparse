package org.innokentiyt.shorewallparser

import org.innokentiyt.shorewallparser.utils.DbConnectionData
import org.innokentiyt.shorewallparser.utils.DbInteractionHelper
import org.innokentiyt.shorewallparser.utils.ShWallParseHelper
import org.innokentiyt.shorewallparser.utils.ShWallParsedStringData
import org.slf4j.Logger
import java.io.File
import java.lang.RuntimeException
import org.slf4j.LoggerFactory

class Application {
    private val logger: Logger = LoggerFactory.getLogger(Application::class.java)

    fun convertDbToConf(dbPath: String) {
        val dbFile = File(dbPath)
        val dbName = dbFile.nameWithoutExtension
        val directory = dbFile.parentFile.absolutePath
        logger.info("Opened file {}", dbFile.absolutePath)

        val dbConnectionData = DbConnectionData(
            pathToDb = directory,
            db = dbName
        )

        //establish a connection to db
        val confDataList: List<ShWallParsedStringData> = DbInteractionHelper.parseDbToList(dbConnectionData)
        ShWallParseHelper.createConfFromList(confDataList, dbName, directory)
    }

    fun convertConfToDb(confPath: String) {
        val confFile = File(confPath)
        val confName = confFile.nameWithoutExtension
        val directory = confFile.parentFile.absolutePath
        logger.info("Opened file {}", confFile.absolutePath)

        val confDataList: List<ShWallParsedStringData> = ShWallParseHelper.parseConfToList(confFile)
        DbInteractionHelper.createDbFromList(confDataList, confName, directory)
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            when {
                args[0].endsWith(".db", ignoreCase = true) -> Application().convertDbToConf(args[0])
                args[0].endsWith(".conf", ignoreCase = true) -> Application().convertConfToDb(args[0])
                else -> throw RuntimeException("ERROR: No correct file option is specified. Exiting...")
            }
        }
    }
}