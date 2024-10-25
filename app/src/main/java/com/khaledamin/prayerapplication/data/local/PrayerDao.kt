package com.khaledamin.prayerapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayers(prayers: ArrayList<PrayerEntity>)

    @Query(value = "SELECT * FROM prayer_days WHERE date >= :date AND date < (:date + 7 * 24 * 60 * 60 * 1000) AND latitude = :latitude AND longitude = :longitude LIMIT 7;")
    suspend fun getPrayers(date: Long, latitude: Double, longitude: Double): List<PrayerEntity>

    @Query(value = "DELETE FROM prayer_days;")
    suspend fun clearRecords()
}