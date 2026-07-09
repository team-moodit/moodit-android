package com.swyp.moodit.common.util

object TextUtil {
    fun attachParticle(word: String): String {
        if (word.isEmpty()) return ""
        val lastChar = word.last()
        return if (lastChar in '\uAC00'..'\uD7A3') {
            val lastCharIndex = (lastChar.code - 0xAC00) % 28
            if (lastCharIndex > 0) "을" else "를"
        } else {
            ""
        }
    }

    fun attachSecondParticle(word: String): String {
        if (word.isEmpty()) return ""
        val lastChar = word.last()
        return if (lastChar in '\uAC00'..'\uD7A3') {
            val lastCharIndex = (lastChar.code - 0xAC00) % 28
            if (lastCharIndex > 0) "이" else "가"
        } else {
            ""
        }
    }
}