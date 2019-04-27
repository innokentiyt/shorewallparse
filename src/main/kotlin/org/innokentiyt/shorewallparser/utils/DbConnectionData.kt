package org.innokentiyt.shorewallparser.utils

data class DbConnectionData(
    val db: String,
    val user: String,
    val password: String,
    val pathToDb: String
) {
    val fullPath: String = "$pathToDb/$db.db"
}
