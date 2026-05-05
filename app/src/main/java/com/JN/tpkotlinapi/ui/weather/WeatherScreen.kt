package com.JN.tpkotlinapi.ui.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.JN.tpkotlinapi.data.model.weatherCodeToInfo
import com.JN.tpkotlinapi.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(vm: WeatherViewModel = viewModel()) {

    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Meteo en direct") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> {
                    val msg = (state as UiState.Error).message
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Erreur : $msg",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { vm.loadWeather() }) {
                            Text("Recharger")
                        }
                    }
                }
                is UiState.Success -> {
                    val w = (state as UiState.Success).data.currentWeather
                    val (desc, emoji) = weatherCodeToInfo(w.weatherCode)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            emoji,
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(
                            "${w.temperature}°C",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            desc,
                            style = MaterialTheme.typography.titleMedium
                        )
                        HorizontalDivider(modifier = Modifier.width(120.dp))
                        Text(
                            "Vent : ${w.windspeed} km/h (${w.windDirection}°)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Mis a jour : ${w.time}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedButton(onClick = { vm.loadWeather() }) {
                            Text("Actualiser")
                        }
                    }
                }
            }
        }
    }
}