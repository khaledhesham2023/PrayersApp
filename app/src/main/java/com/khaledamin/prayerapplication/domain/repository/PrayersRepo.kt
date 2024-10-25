package com.khaledamin.prayerapplication.domain.repository

import com.khaledamin.prayerapplication.data.local.PrayerEntity
import com.khaledamin.prayerapplication.data.model.response.DayDTO
import com.khaledamin.prayerapplication.data.model.response.response.GetQiblaDirectionResponse

interface PrayersRepo {

    suspend fun getOnlineRecords(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): List<DayDTO>

    suspend fun getCachedRecords(): List<PrayerEntity>

    suspend fun insertRecordsIntoCache(
        records: ArrayList<PrayerEntity>
    )

    suspend fun getQibla(latitude: Double,longitude: Double): GetQiblaDirectionResponse

    suspend fun clearRecords()
}