package com.example.nutritiontracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.nutritiontracker.ui.addfood.AddFoodScreen
import com.example.nutritiontracker.ui.goals.GoalsScreen
import com.example.nutritiontracker.ui.home.HomeScreen

@Composable
fun NutritionApp() {
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(Screen.Home, Screen.AddFood, Screen.Goals).forEach { screen ->
                    NavigationBarItem(
                        selected = selectedScreen.route == screen.route,
                        onClick = { selectedScreen = screen },
                        icon = { /* icons later */ },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedScreen) {
            Screen.Home -> HomeScreen()
            Screen.AddFood -> AddFoodScreen()
            Screen.Goals -> GoalsScreen()
        }
    }
}