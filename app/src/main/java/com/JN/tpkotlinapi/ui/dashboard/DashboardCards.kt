package com.JN.tpkotlinapi.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.JN.tpkotlinapi.data.model.ExchangeResponse
import com.JN.tpkotlinapi.data.model.NewsResponse
import com.JN.tpkotlinapi.data.model.WeatherResponse
import com.JN.tpkotlinapi.data.model.weatherCodeToInfo
import com.JN.tpkotlinapi.util.UiState

@Composable
fun CurrencyCard(state: UiState<ExchangeResponse>) {
    DashboardCard(title = "💱 Devises") {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
            is UiState.Error -> Text("Erreur", color = MaterialTheme.colorScheme.error)
            is UiState.Success -> {
                val rates = state.data.conversionRates
                val highlights = listOf("EUR", "GBP", "MGA", "JPY")
                highlights.forEach { code ->
                    rates[code]?.let { rate ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(code, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "%.4f".format(rate),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherCard(state: UiState<WeatherResponse>) {
    DashboardCard(title = "🌤 Météo — Antananarivo") {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
            is UiState.Error -> Text("Erreur", color = MaterialTheme.colorScheme.error)
            is UiState.Success -> {
                val w = state.data.currentWeather
                val (desc, emoji) = weatherCodeToInfo(w.weatherCode)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(emoji, style = MaterialTheme.typography.headlineMedium)
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            "${w.temperature}°C",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(desc, style = MaterialTheme.typography.bodySmall)
                        Text(
                            "Vent ${w.windspeed} km/h",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CryptoCard(state: UiState<Double>) {
    DashboardCard(title = "₿ Bitcoin (USD)") {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
            is UiState.Error -> Text("Erreur", color = MaterialTheme.colorScheme.error)
            is UiState.Success -> {
                Text(
                    "${"$"}%.2f".format(state.data),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun NewsCard(state: UiState<NewsResponse>) {
    DashboardCard(title = "📰 Dernières actualités") {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
            is UiState.Error -> Text("Erreur", color = MaterialTheme.colorScheme.error)
            is UiState.Success -> {
                state.data.articles.take(3).forEach { article ->
                    Text(
                        "• ${article.title}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
            content()
        }
    }
}