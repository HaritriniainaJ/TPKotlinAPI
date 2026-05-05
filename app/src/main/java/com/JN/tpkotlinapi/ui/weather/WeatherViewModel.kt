package com.JN.tpkotlinapi.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.JN.tpkotlinapi.data.model.WeatherResponse
import com.JN.tpkotlinapi.data.repository.WeatherRepository
import com.JN.tpkotlinapi.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repo: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<WeatherResponse>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    // Coordonnees par defaut : Antananarivo, Madagascar
    fun loadWeather(lat: Double = -18.9, lon: Double = 47.5) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { repo.getWeather(lat, lon) }
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it.message) }
        }
    }

    init { loadWeather() }
}