package com.JN.tpkotlinapi.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.JN.tpkotlinapi.data.model.NewsResponse
import com.JN.tpkotlinapi.data.repository.NewsRepository
import com.JN.tpkotlinapi.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repo: NewsRepository = NewsRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<NewsResponse>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadNews(country: String = "fr") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { repo.getTopHeadlines(country) }
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it.message) }
        }
    }

    init { loadNews() }
}