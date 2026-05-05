package com.JN.tpkotlinapi.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.JN.tpkotlinapi.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(vm: DashboardViewModel = viewModel()) {

    val currencyState by vm.currencyState.collectAsState()
    val weatherState by vm.weatherState.collectAsState()
    val newsState by vm.newsState.collectAsState()
    val cryptoState by vm.cryptoState.collectAsState()

    val isRefreshing = currencyState is UiState.Loading ||
            weatherState is UiState.Loading ||
            newsState is UiState.Loading ||
            cryptoState is UiState.Loading

    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tableau de bord") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { vm.loadAll() },
            state = pullToRefreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { CurrencyCard(currencyState) }
                item { WeatherCard(weatherState) }
                item { CryptoCard(cryptoState) }
                item { NewsCard(newsState) }
            }
        }
    }
}