package com.khaledamin.prayerapplication.domain.usecases


import com.khaledamin.prayerapplication.domain.model.Day
import com.khaledamin.prayerapplication.domain.repository.PrayersRepo
import com.khaledamin.prayerapplication.utils.NetworkState
import com.khaledamin.prayerapplication.utils.convertDateToMilliSeconds
import com.khaledamin.prayerapplication.utils.getBeginning
import com.khaledamin.prayerapplication.utils.getSeventhDayInMilliSeconds
import com.khaledamin.prayerapplication.utils.toDay
import com.khaledamin.prayerapplication.utils.toPrayerEntity
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val prayersRepo: PrayersRepo,
    private val networkState: NetworkState
) {

    suspend fun getRecords(
        initialTime: Long,
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): ArrayList<Day> {
        if (networkState.isInternetAvailable()) {
            prayersRepo.clearRecords()
            val monthDaysDTO = prayersRepo.getOnlineRecords(year, month, latitude, longitude)
//            prayersRepo.insertRecordsIntoCache(monthDaysDTO.map { dayDTO ->
//                dayDTO.toPrayerEntity(
//                    initialTime = initialTime,
//                    latitude = latitude,
//                    longitude = longitude
//                )
//            }.toCollection(ArrayList()))
            val monthDays = monthDaysDTO.map {
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
                    prayersRepo.getOnlineRecords(year, month + 1, latitude, longitude)
//                prayersRepo.insertRecordsIntoCache(newMonthDays.map { dayDTO ->
//                    dayDTO.toPrayerEntity(
//                        latitude,
//                        longitude,
//                        initialTime
//                    )
//                }.toCollection(ArrayList()))
                val newMonth = newMonthDays.map { it.toDay(latitude, longitude) }
                daysForWeekFromNow.addAll(newMonth.take(newDaysToAdd))
                return daysForWeekFromNow
            }
            return daysForWeekFromNow
        } else {
            val localRecords = prayersRepo.getCachedRecords(initialTime, latitude, longitude)
                .toCollection(ArrayList())
            return localRecords.map { it.toDay() }.toCollection(ArrayList())
        }
    }
}