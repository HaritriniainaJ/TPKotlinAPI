package com.JN.tpkotlinapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.JN.tpkotlinapi.ui.currency.CurrencyScreen
import com.JN.tpkotlinapi.ui.news.NewsScreen
import com.JN.tpkotlinapi.ui.theme.TPKotlinAPITheme
import com.JN.tpkotlinapi.ui.weather.WeatherScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TPKotlinAPITheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentRoute == "currency",
                                onClick = { navController.navigate("currency") },
                                icon = { Icon(Icons.Default.AttachMoney, null) },
                                label = { Text("Devises") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == "weather",
                                onClick = { navController.navigate("weather") },
                                icon = { Icon(Icons.Default.WbSunny, null) },
                                label = { Text("Meteo") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == "news",
                                onClick = { navController.navigate("news") },
                                icon = { Icon(Icons.AutoMirrored.Filled.Article, null) },
                                label = { Text("News") }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "currency",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("currency") { CurrencyScreen() }
                        composable("weather") { WeatherScreen() }
                        composable("news") { NewsScreen() }
                    }
                }
            }
        }
    }
}