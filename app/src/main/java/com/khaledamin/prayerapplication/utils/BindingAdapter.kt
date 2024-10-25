package com.khaledamin.prayerapplication.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.domain.model.Timing
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("time")
fun convertTimeToHoursMin(textView: TextView, milliSeconds: Long) {
    val dataFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
    val date = Date(milliSeconds)
    textView.text = dataFormat.format(date)
}

//@BindingAdapter("nextPrayer")
//fun getNextPrayer(textView: TextView, timings: ArrayList<Timing>) {
//    val currentDate = System.currentTimeMillis()
//    var foundNextPrayer = false
//    for (timing in timings) {
//        if (currentDate <= timing.time){
//            textView.text = timing.name
//            foundNextPrayer = true
//            break
//        }
//    }
//    if (!foundNextPrayer){
//        textView.text = textView.context.getString(R.string.no_upcoming_prayers)
//    }
