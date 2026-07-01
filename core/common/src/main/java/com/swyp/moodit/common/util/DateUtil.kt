package com.swyp.moodit.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtil {
    fun String.toFormatDate(): String {
        if (this.isBlank()) return ""
        return try {
            val parsedDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val outputFormatter = DateTimeFormatter.ofPattern("yy.MM.dd", Locale.getDefault())
            parsedDate.format(outputFormatter)
        } catch (e: Exception) {
            ""
        }
    }
}