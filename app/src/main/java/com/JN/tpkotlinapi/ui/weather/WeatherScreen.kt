package com.JN.tpkotlinapi.ui.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.JN.tpkotlinapi.data.model.weatherCodeToInfo
import com.JN.tpkotlinapi.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(vm: WeatherViewModel = viewModel()) {

    val state by vm.uiState.collectAsState()
    var latInput by remember { mutableStateOf("-18.9") }
    var lonInput by remember { mutableStateOf("47.5") }
    var showInputs by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Météo en direct") },
                actions = {
                    TextButton(onClick = { showInputs = !showInputs }) {
                        Text(if (showInputs) "Fermer" else "Changer lieu")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Panneau saisie coordonnées
            if (showInputs) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Coordonnées GPS",
                                style = MaterialTheme.typography.titleSmall
                            )
                            OutlinedTextField(
                                value = latInput,
                                onValueChange = { latInput = it },
                                label = { Text("Latitude") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = lonInput,
                                onValueChange = { lonInput = it },
                                label = { Text("Longitude") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Button(
                                onClick = {
                                    val lat = latInput.toDoubleOrNull() ?: -18.9
                                    val lon = lonInput.toDoubleOrNull() ?: 47.5
                                    vm.loadWeather(lat, lon)
                                    showInputs = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Rechercher")
                            }
                        }
                    }
                }
            }

            when (val s = state) {
                is UiState.Loading -> item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Erreur : ${s.message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { vm.loadWeather() }) {
                            Text("Recharger")
                        }
                    }
                }
                is UiState.Success -> {
                    val data = s.data
                    val w = data.currentWeather
                    val (desc, emoji) = weatherCodeToInfo(w.weatherCode)

                    // Météo actuelle
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "📍 ${data.timezone}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(emoji, style = MaterialTheme.typography.displayLarge)
                            Text(
                                "${w.temperature}°C",
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Text(desc, style = MaterialTheme.typography.titleMedium)
                            HorizontalDivider(modifier = Modifier.width(120.dp))
                            Text(
                                "Vent : ${w.windspeed} km/h (${w.windDirection}°)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Mis à jour : ${w.time}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton(onClick = {
                                val lat = latInput.toDoubleOrNull() ?: -18.9
                                val lon = lonInput.toDoubleOrNull() ?: 47.5
                                vm.loadWeather(lat, lon)
                            }) {
                                Text("Actualiser")
                            }
                        }
                    }

                    // Prévisions 7 jours
                    data.daily?.let { daily ->
                        item {
                            Text(
                                "Prévisions 7 jours",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                )
                            )
                        }
                        items(daily.time.size) { i ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            ) {
                                ListItem(
                                    headlineContent = {
                                        Text(daily.time[i])
                                    },
                                    trailingContent = {
                                        Text(
                                            "↑ ${daily.tempMax[i]}°C  ↓ ${daily.tempMin[i]}°C",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}