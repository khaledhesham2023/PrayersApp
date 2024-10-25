package com.khaledamin.prayerapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_days")
data class PrayerEntity(
    @PrimaryKey(autoGenerate = true)
    val prayerId: Long?,
    val readable:String,
    val fajrFormatted: String,
    val fajr: Long,
    val sunriseFormatted: String,
    val sunrise:Long,
    val dhuhrFormatted: String,
    val dhuhr: Long,
    val asrFormatted: String,
    val asr:Long,
    val maghribFormatted: String,
    val maghrib:Long,
    val ishaFormatted: String,
    val isha:Long,
    val dateFormatted: String,
)