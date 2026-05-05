package com.JN.tpkotlinapi.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.JN.tpkotlinapi.data.model.ExchangeResponse
import com.JN.tpkotlinapi.data.model.NewsResponse
import com.JN.tpkotlinapi.data.model.WeatherResponse
import com.JN.tpkotlinapi.data.repository.CryptoRepository
import com.JN.tpkotlinapi.data.repository.CurrencyRepository
import com.JN.tpkotlinapi.data.repository.NewsRepository
import com.JN.tpkotlinapi.data.repository.WeatherRepository
import com.JN.tpkotlinapi.util.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val currencyRepo = CurrencyRepository()
    private val weatherRepo = WeatherRepository()
    private val newsRepo = NewsRepository()
    private val cryptoRepo = CryptoRepository()

    private val _currencyState = MutableStateFlow<UiState<ExchangeResponse>>(UiState.Loading)
    val currencyState: StateFlow<UiState<ExchangeResponse>> = _currencyState

    private val _weatherState = MutableStateFlow<UiState<WeatherResponse>>(UiState.Loading)
    val weatherState: StateFlow<UiState<WeatherResponse>> = _weatherState

    private val _newsState = MutableStateFlow<UiState<NewsResponse>>(UiState.Loading)
    val newsState: StateFlow<UiState<NewsResponse>> = _newsState

    private val _cryptoState = MutableStateFlow<UiState<Double>>(UiState.Loading)
    val cryptoState: StateFlow<UiState<Double>> = _cryptoState

    init { loadAll() }

    fun loadAll() {
        viewModelScope.launch {
            _currencyState.value = UiState.Loading
            _weatherState.value = UiState.Loading
            _newsState.value = UiState.Loading
            _cryptoState.value = UiState.Loading

            // Chargement parallèle des 4 APIs
            val currency = async {
                try { UiState.Success(currencyRepo.getRates("USD")) }
                catch (e: Exception) { UiState.Error(e.message) }
            }
            val weather = async {
                try { UiState.Success(weatherRepo.getWeather(-18.9, 47.5)) }
                catch (e: Exception) { UiState.Error(e.message) }
            }
            val news = async {
                try { UiState.Success(newsRepo.getTopHeadlines()) }
                catch (e: Exception) { UiState.Error(e.message) }
            }
            val crypto = async {
                try { UiState.Success(cryptoRepo.getBitcoinPrice()) }
                catch (e: Exception) { UiState.Error(e.message) }
            }

            _currencyState.value = currency.await()
            _weatherState.value = weather.await()
            _newsState.value = news.await()
            _cryptoState.value = crypto.await()
        }
    }
}