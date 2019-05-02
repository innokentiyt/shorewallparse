package org.innokentiyt.shorewallparser.utils

data class ShWallParsedStringData(
    val action: String,
    val source: Pair<String, String?>,
    val dest: Pair<String, List<String>?>,
    val proto: String,
    val ports: List<String>? = null
)