package com.khaledamin.prayerapplication.data.repository

import com.khaledamin.prayerapplication.data.local.room.PrayerDao
import com.khaledamin.prayerapplication.data.local.room.PrayerEntity
import com.khaledamin.prayerapplication.data.model.response.DayDTO
import com.khaledamin.prayerapplication.data.model.response.response.GetQiblaDirectionResponse
import com.khaledamin.prayerapplication.data.remote.PrayerApi
import com.khaledamin.prayerapplication.domain.repository.PrayersRepo
import javax.inject.Inject

class PrayersRepoImpl @Inject constructor(
    private val api: PrayerApi,
    private val prayerDao: PrayerDao,
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

    override suspend fun getCachedRecords(): List<PrayerEntity> {
        return prayerDao.getPrayers()
    }

    override suspend fun insertRecordsIntoCache(records: ArrayList<PrayerEntity>) {
        prayerDao.insertPrayers(records)
    }

    override suspend fun getQibla(latitude: Double,longitude: Double): GetQiblaDirectionResponse {
        return api.getQiblaDirection(latitude, longitude)
    }


    override suspend fun clearRecords() {
        prayerDao.clearRecords()
    }
}