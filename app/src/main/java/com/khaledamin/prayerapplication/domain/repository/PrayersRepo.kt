package com.khaledamin.prayerapplication.domain.repository

import com.khaledamin.prayerapplication.data.local.PrayerEntity
import com.khaledamin.prayerapplication.data.model.response.DayDTO

interface PrayersRepo {

    suspend fun getOnlineRecords(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): List<DayDTO>

    suspend fun getCachedRecords(
        date: Long,
        latitude: Double,
        longitude: Double
    ): List<PrayerEntity>

    suspend fun insertRecords(
        records: ArrayList<PrayerEntity>
    )
}