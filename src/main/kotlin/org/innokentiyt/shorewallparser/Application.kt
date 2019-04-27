package org.innokentiyt.shorewallparser

import com.typesafe.config.ConfigFactory
import org.innokentiyt.shorewallparser.utils.DbConnectionData
import org.innokentiyt.shorewallparser.utils.DbInteractionHelper
import org.innokentiyt.shorewallparser.utils.ShWallParseHelper
import org.innokentiyt.shorewallparser.utils.ShWallParsedStringData
import org.slf4j.Logger
import java.io.File
import java.lang.RuntimeException
import java.sql.Statement
import org.slf4j.LoggerFactory
import java.sql.Connection
import kotlin.concurrent.fixedRateTimer

class Application {
    private val logger: Logger = LoggerFactory.getLogger(Application::class.java)

    fun convertDbToConf(dbPath: String) {
        val dbFile = File(dbPath)
        val dbName = dbFile.nameWithoutExtension
        val directory = dbFile.parentFile.absolutePath
        logger.info("Opened file {}", dbFile.absolutePath)

        //open and parse config or db file
        val config = ConfigFactory.parseFile(File(dbPath))

        //save config to dataclass
        val dbConnectionData = DbConnectionData(
            pathToDb = config.getString("pathToDb"),
            db = config.getString("dbName"),
            user = config.getString("user"),
            password = config.getString("password")
        )
        logger.info(dbConnectionData.fullPath)

        //establish a connection to db
        val connection = DbInteractionHelper.connectToDb(dbConnectionData)
        val statement: Statement = connection.createStatement()
        statement.queryTimeout = 30 //in seconds
        logger.info(""+statement.queryTimeout)
    }

    fun convertConfToDb(confPath: String) {
        val confFile = File(confPath)
        val confName = confFile.nameWithoutExtension
        val directory = confFile.parentFile.absolutePath
        logger.info("Opened file {}", confFile.absolutePath)

        val confDataList: List<ShWallParsedStringData> = ShWallParseHelper.parseToList(confFile)

        ShWallParseHelper.createDbFromList(confDataList, confName, directory)
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