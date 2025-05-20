package com.example.taskyapplication.agenda.domain

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun Long.toDateAsString(): String =
    LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toTimeAsString(): String =
    LocalTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("h:mm a"))

fun String.timeAsLong(): Long {
    val simpleDate = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDate.parse(this)?.time
        ?: throw IllegalArgumentException("Invalid time string or pattern")

//    val selectedDate = LocalDate.parse(
//        this,
//        DateTimeFormatter.ofPattern("hh:mm")
//    )
//    return selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

// https://stackoverflow.com/questions/26637168/how-to-convert-a-date-to-milliseconds
// https://stackoverflow.com/questions/76323246/unable-to-obtain-localdate-from-temporalaccessor

/*
*
* String myDate = "2014/10/29 18:10:45";
LocalDateTime localDateTime = LocalDateTime.parse(myDate,
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") )
    ;
 */
/*
  With this new Date/Time API, when using a date, you need to
  specify the Zone where the date/time will be used. For your case,
  seems that you want/need to use the default zone of your system.
  Check which zone you need to use for specific behaviour e.g.
  CET or America/Lima


  long millis = localDateTime
    .atZone(ZoneId.systemDefault())
    .toInstant().toEpochMilli();
*/



// Combine date and time strings and convert to LocalDateTime


// Convert LocalDateTime to Long


//val currMoment = LocalDateTime.now()
//val dateTimeAsLong = currMoment.toInstant(ZoneOffset.UTC).toEpochMilli()
//val backToDateTime =
//    LocalDateTime.ofInstant(
//        Instant.ofEpochMilli(dateTimeAsLong),
//        ZoneId.systemDefault()
//    )
//
//val extractDate = backToDateTime.toLocalDate()
//    .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))
//val extractTime = backToDateTime.toLocalTime()
//    .format(java.time.format.DateTimeFormatter.ofPattern("h:mm a"))