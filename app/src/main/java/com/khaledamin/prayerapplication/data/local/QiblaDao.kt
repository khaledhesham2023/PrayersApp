package com.khaledamin.prayerapplication.data.local

import androidx.room.Dao

@Dao
interface QiblaDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertQibla(qiblaEntity: ArrayList<QiblaEntity>)
//
//    @Query(value = "SELECT * FROM qiblas WHERE latitude = :latitude AND longitude = :longitude")
//    fun getQibla(latitude: Double, longitude: Double): QiblaEntity
}