package com.sugesw.ankerpods.util

object Hex {
    /** "08 EE 00" / "08:ee:00" / "08ee00" biçimlerini bayt dizisine çevirir; geçersizse null. */
    fun parse(input: String): ByteArray? {
        val clean = input.filterNot { it.isWhitespace() || it == ':' || it == ',' }
        if (clean.isEmpty() || clean.length % 2 != 0) return null
        if (!clean.all { it in '0'..'9' || it in 'a'..'f' || it in 'A'..'F' }) return null
        return ByteArray(clean.length / 2) { i ->
            clean.substring(2 * i, 2 * i + 2).toInt(16).toByte()
        }
    }

    fun format(bytes: ByteArray): String =
        bytes.joinToString(" ") { "%02X".format(it) }
}
