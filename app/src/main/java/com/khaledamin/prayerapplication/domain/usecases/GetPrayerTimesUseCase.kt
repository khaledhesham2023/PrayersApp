package com.khaledamin.prayerapplication.domain.usecases


import com.khaledamin.prayerapplication.domain.model.Day
import com.khaledamin.prayerapplication.domain.repository.PrayersRepo
import com.khaledamin.prayerapplication.utils.NetworkState
import com.khaledamin.prayerapplication.utils.convertDateToMilliSeconds
import com.khaledamin.prayerapplication.utils.getBeginning
import com.khaledamin.prayerapplication.utils.getSeventhDayInMilliSeconds
import com.khaledamin.prayerapplication.utils.toDay
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val prayersRepo: PrayersRepo,
    private val networkState: NetworkState
) {
    suspend fun getPrayerTimesForWeek(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ArrayList<Day> {
        val monthDays = prayersRepo.getOnlineRecords(year, month, latitude, longitude).map {
            it.toDay(latitude, longitude)
        }.toCollection(ArrayList())

        val beginningOfTheDay = getBeginning()
        val lastDayInWeek = getSeventhDayInMilliSeconds()
        val daysForWeekFromNow = monthDays.filter { day ->
            val dayToCompare = convertDateToMilliSeconds(day.date)
            dayToCompare in beginningOfTheDay..lastDayInWeek
        }.toCollection(ArrayList())
        if (daysForWeekFromNow.size < 7) {
            val newDaysToAdd = 7 - daysForWeekFromNow.size
            val newMonthDays =
                prayersRepo.getOnlineRecords(year, month + 1, latitude, longitude).map {
                    it.toDay(latitude, longitude)
                }.toCollection(ArrayList())
            daysForWeekFromNow.addAll(newMonthDays.take(newDaysToAdd))
            return daysForWeekFromNow
        }
        return daysForWeekFromNow
    }
}