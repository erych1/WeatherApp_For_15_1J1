package com.ulan.weatherapp_for_15_1j.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulan.weatherapp_for_15_1j.data.model.BaseMainResponse
import com.ulan.weatherapp_for_15_1j.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    var liveData = MutableLiveData<BaseMainResponse>()
    init {
        viewModelScope.launch {
            delay(2000)
            _isLoading.value = false
        }

    }
    fun getCurrentWeather(cityName: String) {
        liveData = repository.getWeather(cityName)
    }
}