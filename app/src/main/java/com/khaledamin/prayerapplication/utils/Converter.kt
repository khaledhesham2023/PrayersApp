package com.khaledamin.prayerapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Calendar
import androidx.core.content.ContextCompat
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.data.model.response.Gregorian
import com.khaledamin.prayerapplication.domain.model.Timing
import java.text.SimpleDateFormat
import java.util.Locale


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

/**
 * Gets the next prayer according to list of prayers
 * @param context
 * @param timings list of prayers praying times of the day
 * @return next prayer name based on current time
 */
fun getNextPrayer(context: Context, timings: ArrayList<Timing>): String {
    val currentDate = Calendar.getInstance()
    val timeFormatter = SimpleDateFormat(Constants.HR12_FORMAT, Locale.getDefault())

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