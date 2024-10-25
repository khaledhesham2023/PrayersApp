package com.khaledamin.prayerapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayers(prayers: ArrayList<PrayerEntity>)

    @Query(value = "SELECT * FROM prayer_days;")
    suspend fun getPrayers(): List<PrayerEntity>

    @Query(value = "DELETE FROM prayer_days;")
    suspend fun clearRecords()
}