package com.JN.tpkotlinapi.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.JN.tpkotlinapi.data.model.ExchangeResponse
import com.JN.tpkotlinapi.data.repository.CurrencyRepository
import com.JN.tpkotlinapi.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val repository: CurrencyRepository = CurrencyRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<ExchangeResponse>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    // Devise de base selectionnee
    private val _base = MutableStateFlow("USD")
    val base = _base.asStateFlow()

    init { loadRates() }

    fun loadRates(base: String = "USD") {
        _base.value = base
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { repository.getRates(base) }
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure {
                    _uiState.value = UiState.Error(it.message)
                }
        }
    }
}