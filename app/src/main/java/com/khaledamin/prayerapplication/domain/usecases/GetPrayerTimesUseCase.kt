package com.khaledamin.prayerapplication.domain.usecases


import com.khaledamin.prayerapplication.domain.model.Day
import com.khaledamin.prayerapplication.domain.repository.PrayersRepo
import com.khaledamin.prayerapplication.utils.Constants
import com.khaledamin.prayerapplication.utils.NetworkState
import com.khaledamin.prayerapplication.utils.convertDateToMilliSeconds
import com.khaledamin.prayerapplication.utils.getBeginningOfTheDay
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

        val today = getBeginningOfTheDay()
        val lastDayInWeek = getSeventhDayInMilliSeconds()

        // if internet is available
        if (networkState.isInternetAvailable()) {

            //  Clearing the database for new data uploading from remote network
            prayersRepo.clearRecords()
            val monthDaysDTO = prayersRepo.getOnlineRecords(year, month, latitude, longitude)

            val monthDaysToLoadIntoCache = monthDaysDTO.map { dayDTO ->
                dayDTO.toPrayerEntity(latitude, longitude, initialTime)
            }.filter { prayerEntity ->
                val dayToCompare = convertDateToMilliSeconds(prayerEntity.dateFormatted)
                dayToCompare in today..lastDayInWeek
            }.toCollection(ArrayList())

            // inserting the fetched data into a cache
            prayersRepo.insertRecordsIntoCache(monthDaysToLoadIntoCache)

            // Mapping the DTO days into domain layer object to separate domain from data layer
            val monthDays = monthDaysDTO.map {
                it.toDay(latitude, longitude)
            }.toCollection(ArrayList())

            // filtering the days to get 7 days from today
            val daysForWeekFromNow = monthDays.filter { day ->
                val dayToCompare = convertDateToMilliSeconds(day.date)
                dayToCompare in today..lastDayInWeek
            }.toCollection(ArrayList())

            // A special case if remaining days of the month is less than 7 days
            if (daysForWeekFromNow.size < Constants.WEEK) {

                // getting the difference between current day and week to estimate the number of months
                // to be added to the current list.
                val newDaysToAdd = Constants.WEEK - daysForWeekFromNow.size
                // calling the api again to get days of next month
                val newMonthDays =
                    prayersRepo.getOnlineRecords(year, month + 1, latitude, longitude)

                // adding new days to current ones
                val newMonthDaysDTO = newMonthDays.map { it.toDay(latitude, longitude) }

                // inserting the new days into local database
                prayersRepo.insertRecordsIntoCache(newMonthDays.take(newDaysToAdd).map { dayDTO ->
                    dayDTO.toPrayerEntity(
                        latitude,
                        longitude,
                        initialTime
                    )
                }.toCollection(ArrayList()))
                daysForWeekFromNow.addAll(newMonthDaysDTO.take(newDaysToAdd))
                return daysForWeekFromNow
            }
            return daysForWeekFromNow
        } else {
            // if no network connection occurs
            val localRecords = prayersRepo.getCachedRecords()
            return if (localRecords.isEmpty()){
                ArrayList()
            }else{
                localRecords.map { it.toDay() }.toCollection(ArrayList())
            }
        }
    }
}