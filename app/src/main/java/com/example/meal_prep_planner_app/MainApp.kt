package com.example.meal_prep_planner_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meal_prep_planner_app.screens.HomeScreen
import com.example.meal_prep_planner_app.screens.ProfileScreen
import com.example.meal_prep_planner_app.screens.SearchMealsScreen
import com.example.meal_prep_planner_app.screens.WeeklyPlannerScreen
import com.example.meal_prep_planner_app.ui.components.BottomNavBar
import com.example.meal_prep_planner_app.ui.components.BottomNavItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(navController: NavController) {
    val mainNavController = rememberNavController()

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Weekly Planner", Icons.Default.DateRange, "weekly"),
        BottomNavItem("Search Meals", Icons.Default.Search, "search"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = items,
                navController = mainNavController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("weekly") { WeeklyPlannerScreen() }
            composable("search") { SearchMealsScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}
