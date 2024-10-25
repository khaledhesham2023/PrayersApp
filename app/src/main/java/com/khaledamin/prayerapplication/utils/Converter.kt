package com.khaledamin.prayerapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Calendar
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.data.model.response.Gregorian
import com.khaledamin.prayerapplication.domain.model.Timing
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertDatetimeToMilliSeconds(gregorian: Gregorian, prayerTime: String): Long {
    val actualTime = prayerTime.substringBefore(" ")

    // 24 hr format
    val inputFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())
    // 12 hr format
    val outputFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val timeIn24hrFormat = inputFormatter.parse(actualTime)
    val timeIn12hrFormat = outputFormatter.format(timeIn24hrFormat!!)

    val accurateTime = "${gregorian.date} $timeIn12hrFormat"

    val accurateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault())
    return accurateFormat.parse(accurateTime)?.time ?: 0L
}

fun getSeventhDayInMilliSeconds(): Long {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 7)
    return calendar.timeInMillis
}

fun convertDateToMilliSeconds(readable: String): Long {
    val inputFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return inputFormatter.parse(readable)?.time!!
}

fun convertDateFormat(format: String, inputDate: String): String {
    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
    val outputFormat = SimpleDateFormat(Constants.UIDayFormat, Locale.getDefault())

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}

fun convertTo12HrFormat(prayerTime: String): String {
    val actualTime = prayerTime.substringBefore(" ")

    // 24 hr format
    val inputFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())
    // 12 hr format
    val outputFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

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

fun drawableToBitmap(context: Context, drawableId: Int): Bitmap? {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    val bitmap = Bitmap.createBitmap(
        drawable?.intrinsicWidth ?: 1,
        drawable?.intrinsicHeight ?: 1,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)

    return bitmap
}

fun getNextPrayer(context: Context, timings: ArrayList<Timing>): String {
    val currentDate = Calendar.getInstance()
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    // Get the current time as a string
    val currentTimeString = timeFormatter.format(currentDate.time)
    val currentTime = timeFormatter.parse(currentTimeString)!!

    // Variable to hold the next prayer time
    var nextPrayer: Timing? = null

    for (timing in timings) {
        val dateToCompare = timeFormatter.parse(timing.timeInHourMinute)!!

        // Compare only the time parts
        if (currentTime <= dateToCompare) {
            if (nextPrayer == null || dateToCompare.before(timeFormatter.parse(nextPrayer.timeInHourMinute))) {
                nextPrayer = timing
            }
        }
    }
    // Return the next prayer time or a default message if none are found
    return nextPrayer?.name ?: context.getString(R.string.no_upcoming_prayers)
}

fun convertDatetimeToMilliSeconds(date:String, prayerTime: String): Long {
    val actualTime = prayerTime.substringBefore(" ")

    // 24 hr format
    val inputFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())
    // 12 hr format
    val outputFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val timeIn24hrFormat = inputFormatter.parse(actualTime)
    val timeIn12hrFormat = outputFormatter.format(timeIn24hrFormat!!)

    val accurateTime = "$date $timeIn12hrFormat"

    val accurateFormat = SimpleDateFormat("dd MM yyyy HH:mm a", Locale.getDefault())
    return accurateFormat.parse(accurateTime)?.time ?: 0L
}