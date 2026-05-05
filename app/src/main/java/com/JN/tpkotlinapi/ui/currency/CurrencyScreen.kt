package com.JN.tpkotlinapi.ui.currency

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.JN.tpkotlinapi.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(vm: CurrencyViewModel = viewModel()) {

    val state by vm.uiState.collectAsState()
    val base by vm.base.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val priorityCurrencies = listOf(
        "EUR", "GBP", "JPY", "CHF", "CAD", "AUD", "MGA", "ZAR", "CNY", "INR"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cours des devises") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Champ de recherche
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it.uppercase() },
                placeholder = { Text("Rechercher une devise (ex: EUR)") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Selecteur devise de base
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("USD", "EUR", "GBP").forEach { currency ->
                    FilterChip(
                        selected = base == currency,
                        onClick = { vm.loadRates(currency) },
                        label = { Text(currency) }
                    )
                }
            }

            when (state) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> {
                    val msg = (state as UiState.Error).message
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Erreur de chargement",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                msg ?: "Inconnu",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(onClick = { vm.loadRates(base) }) {
                                Text("Reessayer")
                            }
                        }
                    }
                }
                is UiState.Success -> {
                    val data = (state as UiState.Success).data
                    val rates = data.conversionRates

                    // Filtrage par recherche
                    val filtered = rates.entries.filter {
                        it.key.contains(searchQuery, ignoreCase = true)
                    }

                    // Devises prioritaires d'abord, puis reste alphabetique
                    val sorted = if (searchQuery.isEmpty()) {
                        (
                                priorityCurrencies.mapNotNull { code ->
                                    rates[code]?.let { code to it }
                                } +
                                        rates.entries
                                            .filter { it.key !in priorityCurrencies }
                                            .sortedBy { it.key }
                                            .map { it.key to it.value }
                                )
                    } else {
                        filtered.sortedBy { it.key }.map { it.key to it.value }
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item {
                            Text(
                                "Mis a jour : ${data.lastUpdate.take(16)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        items(sorted) { (code, rate) ->
                            CurrencyRow(
                                code = code,
                                rate = rate,
                                base = base,
                                isPriority = code in priorityCurrencies && searchQuery.isEmpty()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyRow(
    code: String,
    rate: Double,
    base: String,
    isPriority: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isPriority)
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        ListItem(
            headlineContent = {
                Text(code, style = MaterialTheme.typography.titleSmall)
            },
            trailingContent = {
                Text(
                    "%.4f %s".format(rate, base),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}