package org.innokentiyt.shorewallparser.utils

data class DbConnectionData(
    val pathToDb: String,
    val db: String
) {
    val fullPath: String = "$pathToDb/$db.db"
}
