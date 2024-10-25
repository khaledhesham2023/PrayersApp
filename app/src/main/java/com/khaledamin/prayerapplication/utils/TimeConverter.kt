package com.khaledamin.prayerapplication.utils

import android.icu.util.Calendar
import com.khaledamin.prayerapplication.utils.Constants.Companion.GREGORIAN_DAY_FORMAT
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Converts Datetime into millisecond
 *
 * @param date date "dd-MM-yyyy"
 * @param prayerTime time in EEST UTC+03:00 time zone
 * @return time in milliseconds
 */
fun convertDatetimeToMilliSeconds(date:String, prayerTime: String): Long {
    val actualTime = prayerTime.substringBefore(" ")

    // 24 hr format
    val inputFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())
    // 12 hr format
    val outputFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val timeIn24hrFormat = inputFormatter.parse(actualTime)
    val timeIn12hrFormat = outputFormatter.format(timeIn24hrFormat!!)

    val accurateTime = "$date $timeIn12hrFormat"

    val accurateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault())
    return accurateFormat.parse(accurateTime)?.time ?: 0L
}

/**
 * Gets the 7th day from today
 * @return 7th day in milliseconds
 */
fun getSeventhDayInMilliSeconds(): Long {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 7)
    return calendar.timeInMillis
}
/**
 * Converts given date into milliseconds
 * @param readable date in string format
 * @return date in milliseconds
 */

fun convertDateToMilliSeconds(readable: String): Long {
    val inputFormatter = SimpleDateFormat(GREGORIAN_DAY_FORMAT, Locale.getDefault())
    return inputFormatter.parse(readable)?.time!!
}

/**
 *  Gets UI date format
 *  @param format current date format
 *  @param inputDate date to be transformed
 *  @return date as a string
 */
fun convertDateFormat(format: String, inputDate: String): String {
    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
    val outputFormat = SimpleDateFormat(Constants.UI_DAY_FORMAT, Locale.getDefault())

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}

/**
 * Converts prayer time into 12 hr format
 * @param prayerTime in EEST timezone.
 * @return prayer time in 12hr format
 */
fun convertTo12HrFormat(prayerTime: String): String {
    val actualTime = prayerTime.substringBefore(" ")

    // 24 hr format
    val inputFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())
    // 12 hr format
    val outputFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val timeIn24hrFormat = inputFormatter.parse(actualTime)
    return outputFormatter.format(timeIn24hrFormat!!)
}

/**
 * resets the today to 00:00:00:0000 12:00 AM
 */
fun getBeginningOfTheDay(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}