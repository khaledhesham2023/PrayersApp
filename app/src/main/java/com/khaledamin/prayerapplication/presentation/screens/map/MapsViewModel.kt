package com.khaledamin.prayerapplication.presentation.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaledamin.prayerapplication.domain.model.Qibla
import com.khaledamin.prayerapplication.domain.usecases.GetCurrentQiblaUsecase
import com.khaledamin.prayerapplication.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getCurrentQiblaUsecase: GetCurrentQiblaUsecase
) : ViewModel() {

    private val _getQiblaLiveData = MutableLiveData<State<Qibla>>()
    val getQiblaLiveData: LiveData<State<Qibla>>
        get() = _getQiblaLiveData

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress


    fun getCurrentQibla(latitude: Double, longitude: Double) =
        viewModelScope.launch(provideCoroutineExceptionHandler(liveData = _getQiblaLiveData)) {
            _showProgress.value = true
            try {
               _getQiblaLiveData.value = State.Success(getCurrentQiblaUsecase.getQiblaDirection(latitude, longitude))
            } finally {
                _showProgress.value = false
            }
        }

    private fun <T> provideCoroutineExceptionHandler(liveData: MutableLiveData<State<T>>): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            _showProgress.value = false
            liveData.value = State.Error(message = exception.message!!)
        }
}