package com.khaledamin.prayerapplication.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaledamin.prayerapplication.domain.model.Day
import com.khaledamin.prayerapplication.domain.usecases.GetPrayerTimesUseCase
import com.khaledamin.prayerapplication.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase
) : ViewModel() {

    private val _getPrayersLiveData = MutableLiveData<State<ArrayList<Day>>>()
    val getPrayersLiveData: LiveData<State<ArrayList<Day>>>
        get() = _getPrayersLiveData

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _updateDayLiveData = MutableLiveData<Int>()
    val updateDayLiveData:LiveData<Int>
        get() = _updateDayLiveData


    fun getWeekPrayers(initialTime:Long, year: Int, month: Int, latitude: Double, longitude: Double) =
        viewModelScope.launch(provideCoroutineExceptionHandler(_getPrayersLiveData)) {
            _showProgress.value = true
            try {
                val response =
                    getPrayerTimesUseCase.getRecords(initialTime,year, month, latitude, longitude)
                _getPrayersLiveData.value = State.Success(data = response)
                _getPrayersLiveData.value
            } finally {
                _showProgress.value = false
            }
        }

    private fun <T> provideCoroutineExceptionHandler(liveData: MutableLiveData<State<T>>): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            _showProgress.value = false
            liveData.value = State.Error(message = exception.message!!)
        }
    }
    fun updateDay(day:Int){
        _updateDayLiveData.value = day
    }
}