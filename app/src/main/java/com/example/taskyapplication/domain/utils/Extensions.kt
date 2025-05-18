package com.example.taskyapplication.domain.utils

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
        .format(java.time.format.DateTimeFormatter.ofPattern("h:mm a"))

fun String.timeAsLong(): Long {
    val simpleDate = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDate.parse(this)?.time ?: throw IllegalArgumentException("Invalid time string or pattern")
//    val selectedDate = LocalDate.parse(
//        this,
//        DateTimeFormatter.ofPattern("hh:mm")
//    )
//    return selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

//Convert date and time to long
// https://stackoverflow.com/questions/26637168/how-to-convert-a-date-to-milliseconds
// https://stackoverflow.com/questions/76323246/unable-to-obtain-localdate-from-temporalaccessor

/*
*
* String myDate = "2014/10/29 18:10:45";
LocalDateTime localDateTime = LocalDateTime.parse(myDate,
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );
/*
  With this new Date/Time API, when using a date, you need to
  specify the Zone where the date/time will be used. For your case,
  seems that you want/need to use the default zone of your system.
  Check which zone you need to use for specific behaviour e.g.
  CET or America/Lima
*/
long millis = localDateTime
    .atZone(ZoneId.systemDefault())
    .toInstant().toEpochMilli();
*
*
*
* */