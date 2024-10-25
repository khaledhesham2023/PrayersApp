package com.khaledamin.prayerapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khaledamin.prayerapplication.domain.model.Qibla

@Dao
interface QiblaDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertQibla(qiblaEntity: ArrayList<QiblaEntity>)
//
//    @Query(value = "SELECT * FROM qiblas WHERE latitude = :latitude AND longitude = :longitude")
//    fun getQibla(latitude: Double, longitude: Double): QiblaEntity
}