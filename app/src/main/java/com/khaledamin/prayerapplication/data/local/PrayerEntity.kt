package com.khaledamin.prayerapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khaledamin.prayerapplication.domain.model.Timing

@Entity(tableName = "prayer_days")
data class PrayerEntity(
    @PrimaryKey(autoGenerate = true)
    val prayerId: Long,
    val readable:String,
    val fajr: String,
    val sunrise:String,
    val dhuhr:String,
    val asr:String,
    val maghrib:String,
    val isha:String,
    val date:Long,
    val latitude:Double,
    val longitude:Double
)