package org.innokentiyt.shorewallparser

import com.typesafe.config.ConfigFactory
import org.innokentiyt.shorewallparser.utils.DbConnectionData
import org.innokentiyt.shorewallparser.utils.DbInteractionHelper
import org.slf4j.Logger
import java.io.File
import java.lang.RuntimeException
import java.sql.Statement
import org.slf4j.LoggerFactory

class Application {
    fun initialize(args: Array<String>) {
        val logger: Logger = LoggerFactory.getLogger(Application::class.java)

        //open and parse config file
        if (args[0].isEmpty() ) throw RuntimeException("ERROR: No config file option specified.")
        val config = ConfigFactory.parseFile(File(args[0]))

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

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Application().initialize(args)
        }
    }
}