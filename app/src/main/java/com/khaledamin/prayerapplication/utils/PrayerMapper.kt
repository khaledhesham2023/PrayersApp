package com.khaledamin.prayerapplication.utils

import com.khaledamin.prayerapplication.data.local.PrayerEntity
import com.khaledamin.prayerapplication.data.model.response.DayDTO
import com.khaledamin.prayerapplication.data.model.response.response.QiblaDTO
import com.khaledamin.prayerapplication.domain.model.Day
import com.khaledamin.prayerapplication.domain.model.Qibla
import com.khaledamin.prayerapplication.domain.model.Timing

fun DayDTO.toDay(latitude:Double,longitude:Double): Day {
    return Day(
        timings = arrayListOf(
            Timing(
                name = Constants.FAJR,
                time = convertDatetimeToMilliSeconds(
                    date = this.date.gregorian.date,
                    prayerTime = timings.fajr
                ),
                timeInHourMinute = convertTo12HrFormat(timings.fajr)
            ),
            Timing(
                name = Constants.SUNRISE,
                time = convertDatetimeToMilliSeconds(
                    date = this.date.gregorian.date,
                    prayerTime = timings.sunrise
                ),
                timeInHourMinute = convertTo12HrFormat(timings.sunrise)
            ),
            Timing(
                name = Constants.DHUHR,
                time = convertDatetimeToMilliSeconds(
                    date = this.date.gregorian.date,
                    prayerTime = timings.dhuhr
                ),
                timeInHourMinute = convertTo12HrFormat(timings.dhuhr)
            ),
            Timing(
                name = Constants.ASR,
                time = convertDatetimeToMilliSeconds(
                    date = this.date.gregorian.date,
                    prayerTime = timings.asr
                ),
                timeInHourMinute = convertTo12HrFormat(timings.asr)
            ),
            Timing(
                name = Constants.MAGHRIB,
                time = convertDatetimeToMilliSeconds(
                    date = this.date.gregorian.date,
                    prayerTime = timings.maghrib
                ),
                timeInHourMinute = convertTo12HrFormat(timings.maghrib)
            ),
            Timing(
                name = Constants.ISHA,
                time = convertDatetimeToMilliSeconds(
                    date = this.date.gregorian.date,
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

fun DayDTO.toPrayerEntity(latitude: Double,longitude: Double,initialTime:Long): PrayerEntity {
    return PrayerEntity(
        prayerId = null,
        readable = this.date.readable,
        fajrFormatted = convertTo12HrFormat(this.timings.fajr),
        fajr = convertDatetimeToMilliSeconds(
            this.date.gregorian.date,
            convertTo12HrFormat(timings.fajr)
        ),
        sunriseFormatted = convertTo12HrFormat(this.timings.sunrise),
        sunrise = convertDatetimeToMilliSeconds(
            this.date.gregorian.date,
            convertTo12HrFormat(timings.sunrise)
        ),
        dhuhrFormatted = convertTo12HrFormat(this.timings.dhuhr),
        dhuhr = convertDatetimeToMilliSeconds(
            this.date.gregorian.date,
            convertTo12HrFormat(timings.dhuhr)
        ),
        asrFormatted = convertTo12HrFormat(this.timings.asr),
        asr = convertDatetimeToMilliSeconds(
            this.date.gregorian.date,
            convertTo12HrFormat(timings.asr)
        ),
        maghribFormatted = convertTo12HrFormat(this.timings.maghrib),
        maghrib = convertDatetimeToMilliSeconds(
            this.date.gregorian.date,
            convertTo12HrFormat(timings.maghrib)
        ),
        ishaFormatted = convertTo12HrFormat(this.timings.isha),
        isha = convertDatetimeToMilliSeconds(
            this.date.gregorian.date,
            convertTo12HrFormat(timings.isha)
        ),
        latitude = latitude,
        longitude = longitude,
        dateFormatted = this.date.gregorian.date,
        date = initialTime
    )
}

fun QiblaDTO.toQibla(): Qibla {
    return Qibla(
        latitude = this.latitude,
        longitude = this.longitude,
        direction = this.direction
    )
}

fun PrayerEntity.toDay():Day {
    return Day(
        arrayListOf(
            Timing(
                name = Constants.FAJR,
                time = this.fajr,
                timeInHourMinute = this.fajrFormatted
            ),
            Timing(
                name = Constants.SUNRISE,
                time = this.sunrise,
                timeInHourMinute = this.sunriseFormatted
            ),
            Timing(
                name = Constants.DHUHR,
                time = this.dhuhr,
                timeInHourMinute = this.dhuhrFormatted
            ),
            Timing(
                name = Constants.ASR,
                time = this.asr,
                timeInHourMinute = this.asrFormatted
            ),
            Timing(
                name = Constants.MAGHRIB,
                time = this.maghrib,
                timeInHourMinute = this.maghribFormatted
            ),
            Timing(
                name = Constants.ISHA,
                time = this.isha,
                timeInHourMinute = this.ishaFormatted
            )
        ),
        readable = this.readable,
        date = this.dateFormatted,
        latitude = this.latitude,
        longitude = this.longitude
    )
}