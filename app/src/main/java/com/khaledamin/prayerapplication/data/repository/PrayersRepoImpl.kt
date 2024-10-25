package com.khaledamin.prayerapplication.data.repository

import com.khaledamin.prayerapplication.data.local.PrayerDao
import com.khaledamin.prayerapplication.data.local.PrayerEntity
import com.khaledamin.prayerapplication.data.model.response.DayDTO
import com.khaledamin.prayerapplication.data.remote.PrayerApi
import com.khaledamin.prayerapplication.domain.repository.PrayersRepo
import javax.inject.Inject

class PrayersRepoImpl @Inject constructor(
    private val api: PrayerApi,
    private val dao: PrayerDao
) : PrayersRepo {
    override suspend fun getOnlineRecords(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): List<DayDTO> {
        return api.getPrayerTimes(
            year = year,
            month = month,
            latitude = latitude,
            longitude = longitude
        ).daysDTO
    }

    override suspend fun getCachedRecords(
        date: Long,
        latitude: Double,
        longitude: Double
    ): List<PrayerEntity> {
        return dao.getPrayers(date = date, latitude = latitude, longitude = longitude)
    }

    override suspend fun insertRecords(records: ArrayList<PrayerEntity>) {
        dao.insertPrayers(records)
    }

}