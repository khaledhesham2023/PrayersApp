package com.khaledamin.prayerapplication.domain.usecases

import com.khaledamin.prayerapplication.data.model.response.response.QiblaDTO
import com.khaledamin.prayerapplication.domain.model.Qibla
import com.khaledamin.prayerapplication.domain.repository.PrayersRepo
import com.khaledamin.prayerapplication.utils.toQibla
import javax.inject.Inject

class GetCurrentQiblaUsecase @Inject constructor(
    private val prayersRepo: PrayersRepo
) {

    suspend fun getQiblaDirection(latitude: Double, longitude: Double): Qibla {
        return prayersRepo.getQibla(latitude, longitude).qibla.toQibla()
    }
}