package org.innokentiyt.shorewallparser.utils

import org.innokentiyt.shorewallparser.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

class DbInteractionHelper {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Application::class.java)
        @JvmStatic
        fun createDb(dbConnectionData: DbConnectionData): Connection {
            return try {
                DriverManager.getConnection("jdbc:sqlite:${dbConnectionData.fullPath}").also {
                    logger.info("connected to db")
                }
            } catch (se: SQLException) {
                logger.error(se.toString())
                throw se
            }
        }

        fun createDbFromList(confDataList: List<ShWallParsedStringData>, confName: String, directory: String) {
            val dbConnectionData = DbConnectionData(
                pathToDb = directory,
                db = confName+Date().toInstant().epochSecond
            )
            val connection = createDb(dbConnectionData)
            connection.autoCommit = false
            val statement = connection.createStatement()
            val createTableCmd = """CREATE TABLE "SHOREWALL" (
                "rule"	TEXT NOT NULL,
                "source_zone"	TEXT NOT NULL,
                "source_host"	TEXT NOT NULL,
                "dest_zone"	TEXT NOT NULL,
                "dest_hosts"	TEXT,
                "proto"	TEXT NOT NULL,
                "ports"	TEXT
            );""".trimIndent()
            statement.executeUpdate(createTableCmd)
            statement.close()

            val sortedList = confDataList.sortedWith(compareBy(
                { it.action },
                { it.source.first },
                { it.source.second },
                { it.dest.first },
                { it.dest.second?.joinToString() },
                { it.proto },
                { it.ports?.joinToString() }
            ))
            System.out.println(sortedList.size)
            try {
                sortedList.forEach {
                    statement.executeUpdate("insert into 'main'.'SHOREWALL' values('${it.action}','${it.source.first}','${it.source.second}','${it.dest.first}',${it.dest.second?.joinToString(prefix = "'",postfix = "'") ?: "NULL"},'${it.proto}',${it.ports?.joinToString(prefix = "'",postfix = "'") ?: "NULL"})")
                }
                statement.close()
                connection.commit()
                connection.close()
            } catch (se: SQLException) {
                throw se
            }
        }

        fun parseDbToList(dbConnectionData: DbConnectionData): List<ShWallParsedStringData> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}