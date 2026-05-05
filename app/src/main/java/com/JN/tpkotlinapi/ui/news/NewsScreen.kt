package com.JN.tpkotlinapi.ui.news

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import com.JN.tpkotlinapi.util.UiState
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(vm: NewsViewModel = viewModel()) {

    val state by vm.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Actualités") })
        }
    ) { padding ->
        when (state) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                val msg = (state as UiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Erreur : $msg",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { vm.loadNews() }) {
                            Text("Recharger")
                        }
                    }
                }
            }
            is UiState.Success -> {
                val articles = (state as UiState.Success).data.articles
                LazyColumn(contentPadding = padding) {
                    items(articles) { article ->
                        NewsCard(
                            article = article,
                            onClick = { uriHandler.openUri(article.url) }
                        )
                    }
                }
            }
        }
    }
}