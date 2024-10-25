package com.khaledamin.prayerapplication.utils

import android.icu.util.Calendar
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.khaledamin.prayerapplication.data.model.response.Gregorian
import java.text.SimpleDateFormat
import java.util.Locale

fun convertDatetimeToMilliSeconds(gregorian:Gregorian, prayerTime:String):Long{
    val actualTime = prayerTime.substringBefore(" ")

    // 24 hr format
    val inputFormatter = SimpleDateFormat("hh:mm",Locale.getDefault())
    // 12 hr format
    val outputFormatter = SimpleDateFormat("hh:mm a",Locale.getDefault())

    val timeIn24hrFormat = inputFormatter.parse(actualTime)
    val timeIn12hrFormat = outputFormatter.format(timeIn24hrFormat!!)

    val accurateTime = "${gregorian.date} $timeIn12hrFormat"

    val accurateFormat = SimpleDateFormat("${gregorian.format} HH:mm a", Locale.getDefault())
    return accurateFormat.parse(accurateTime)?.time ?: 0L
}

fun getSeventhDayInMilliSeconds():Long{
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH,7)
    return calendar.timeInMillis
}
fun convertDateToMilliSeconds(readable:String):Long{
    val inputFormatter = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())
    return inputFormatter.parse(readable)?.time!!
}
fun convertDateFormat(format:String, inputDate: String):String {
    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
    val outputFormat = SimpleDateFormat(Constants.UIDayFormat, Locale.getDefault())

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}
fun convertTo12HrFormat(prayerTime:String):String{
    val actualTime = prayerTime.substringBefore(" ")

    // 24 hr format
    val inputFormatter = SimpleDateFormat("hh:mm",Locale.getDefault())
    // 12 hr format
    val outputFormatter = SimpleDateFormat("hh:mm a",Locale.getDefault())

    val timeIn24hrFormat = inputFormatter.parse(actualTime)
    return outputFormatter.format(timeIn24hrFormat!!)
}

fun getBeginning(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}