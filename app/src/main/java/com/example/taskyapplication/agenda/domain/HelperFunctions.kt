package com.example.taskyapplication.agenda.domain

import android.content.ContentResolver
import android.net.Uri
import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_DAY_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_HOUR_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_SIX_HOURS_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_TEN_MINUTES_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_THIRTY_MINUTES_BEFORE
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toDateAsString(): String =
    LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

fun Long.toTimeAsString(): String =
    LocalTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("h:mm a"))

fun String.timeAsLong(): Long {
    val simpleDate = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDate.parse(this)?.time
        ?: throw IllegalArgumentException("Invalid time string or pattern")
}

fun getReminderOption(
    selection: String,
) : ReminderOptions {
    return when (selection) {
        REMINDER_TEN_MINUTES_BEFORE -> ReminderOptions.TEN_MINUTES_BEFORE
        REMINDER_THIRTY_MINUTES_BEFORE -> ReminderOptions.THIRTY_MINUTES_BEFORE
        REMINDER_ONE_HOUR_BEFORE -> ReminderOptions.ONE_HOUR_BEFORE
        REMINDER_ONE_DAY_BEFORE -> ReminderOptions.ONE_DAY_BEFORE
        REMINDER_SIX_HOURS_BEFORE -> ReminderOptions.SIX_HOURS_BEFORE
        else -> ReminderOptions.THIRTY_MINUTES_BEFORE // default value
    }
}

fun Uri.toImageByteArray(contentResolver: ContentResolver): ByteArray? {
    return try {
        contentResolver.openInputStream(this)?.use { inputStream ->
            val outputStream = ByteArrayOutputStream()
            inputStream.copyTo(outputStream)
            outputStream.toByteArray()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
