package com.khaledamin.prayerapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qiblas")
data class QiblaEntity(
    @PrimaryKey(autoGenerate = true)
    val qiblaId:Long,
    val latitude: Double,
    val longitude: Double
)
