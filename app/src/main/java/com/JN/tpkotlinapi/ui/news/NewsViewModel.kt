package com.JN.tpkotlinapi.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.JN.tpkotlinapi.data.model.NewsResponse
import com.JN.tpkotlinapi.data.repository.NewsRepository
import com.JN.tpkotlinapi.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<UiState<NewsResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<NewsResponse>> = _uiState

    init { loadNews() }

    fun loadNews() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                _uiState.value = UiState.Success(repository.getTopHeadlines())
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }
}