package org.innokentiyt.shorewallparser.utils

import java.net.Inet4Address

data class ShWallParsedStringData(
    val action: String,
    val source: Map<String, Inet4Address?>,
    val dest: Map<String, List<Inet4Address>?>,
    val proto: String,
    val ports: List<Int>?
)