package org.innokentiyt.shorewallparser.utils

import org.innokentiyt.shorewallparser.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DbInteractionHelper {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Application::class.java)
        @JvmStatic
        fun connectToDb(dbConnectionData: DbConnectionData): Connection {
            return try {
                DriverManager.getConnection("jdbc:sqlite:${dbConnectionData.fullPath}").also {
                    logger.info("connected to db")
                }
            } catch (se: SQLException) {
                logger.error(se.toString())
                throw se
            }
        }
    }
}