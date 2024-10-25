package com.khaledamin.prayerapplication.utils

import com.khaledamin.prayerapplication.data.model.response.DayDTO
import com.khaledamin.prayerapplication.domain.model.Day
import com.khaledamin.prayerapplication.domain.model.Timing

fun DayDTO.toDay(latitude:Double,longitude:Double): Day {
    return Day(
        timings = arrayListOf(
            Timing(
                name = Constants.FAJR,
                time = convertDatetimeToMilliSeconds(
                    gregorian = this.date.gregorian,
                    prayerTime = timings.fajr
                ),
                timeInHourMinute = convertTo12HrFormat(timings.fajr)
            ),
            Timing(
                name = Constants.SUNRISE,
                time = convertDatetimeToMilliSeconds(
                    gregorian = this.date.gregorian,
                    prayerTime = timings.sunrise
                ),
                timeInHourMinute = convertTo12HrFormat(timings.sunrise)
            ),
            Timing(
                name = Constants.DHUHR,
                time = convertDatetimeToMilliSeconds(
                    gregorian = this.date.gregorian,
                    prayerTime = timings.dhuhr
                ),
                timeInHourMinute = convertTo12HrFormat(timings.dhuhr)
            ),
            Timing(
                name = Constants.ASR,
                time = convertDatetimeToMilliSeconds(
                    gregorian = this.date.gregorian,
                    prayerTime = timings.asr
                ),
                timeInHourMinute = convertTo12HrFormat(timings.asr)
            ),
            Timing(
                name = Constants.MAGHRIB,
                time = convertDatetimeToMilliSeconds(
                    gregorian = this.date.gregorian,
                    prayerTime = timings.maghrib
                ),
                timeInHourMinute = convertTo12HrFormat(timings.maghrib)
            ),
            Timing(
                name = Constants.ISHA,
                time = convertDatetimeToMilliSeconds(
                    gregorian = this.date.gregorian,
                    prayerTime = timings.isha
                ),
                timeInHourMinute = convertTo12HrFormat(timings.isha)
            )
        ),
        readable = convertDateFormat(Constants.DATAFORMAT,this.date.readable),
        latitude = latitude,
        longitude = longitude,
        date = this.date.gregorian.date
    )
}