package com.swyp.moodit.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
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

    fun String.toDaysAgoMessage(): String {
        if (this.isBlank()) return ""
        return try {
            val parsedDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val today = LocalDate.now()
            val daysAgo = ChronoUnit.DAYS.between(parsedDate, today)
            when {
                daysAgo <= 0L -> "오늘"
                daysAgo == 1L -> "어제"
                else -> "${daysAgo}일 전"
            }
        } catch (e: Exception) {
            ""
        }
    }
}