package com.khaledamin.prayerapplication.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khaledamin.prayerapplication.data.local.QiblaDao

@Database(entities = [PrayerEntity::class], version = 1)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerDao() : PrayerDao
    abstract fun qiblaDao(): QiblaDao
}