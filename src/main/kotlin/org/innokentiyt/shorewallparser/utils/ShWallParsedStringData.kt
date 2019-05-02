package org.innokentiyt.shorewallparser.utils

data class ShWallParsedStringData(
    val action: String,
    val source: Map<String, String?>,
    val dest: Map<String, List<String>?>,
    val proto: String,
    val ports: List<String>? = null
)