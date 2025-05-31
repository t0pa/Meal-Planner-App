package com.example.meal_prep_planner_app.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.meal_prep_planner_app.ui.viewmodel.UserViewModel
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.meal_prep_planner_app.ui.screens.HomeScreen
import com.example.meal_prep_planner_app.ui.screens.LoginScreen
import com.example.meal_prep_planner_app.ui.screens.ProfileScreen
import com.example.meal_prep_planner_app.ui.screens.SearchMealsScreen
import com.example.meal_prep_planner_app.ui.screens.WeeklyPlannerScreen
import com.example.meal_prep_planner_app.ui.screens.navigation.BottomNavBar
import com.example.meal_prep_planner_app.ui.screens.navigation.BottomNavItem

@Serializable object Auth
@Serializable object Login
@Serializable object Register

@Serializable object Main
@Serializable object Home
@Serializable object Search
@Serializable object Profile
@Serializable object Weekly

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(userViewModel: UserViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val loggedUser by userViewModel.loggedUser.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    val showBottomBar = currentRoute in listOf(
        Home::class.qualifiedName,
        Search::class.qualifiedName,
        Weekly::class.qualifiedName,
        Profile::class.qualifiedName
    )

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LaunchedEffect(loggedUser) {
        if (loggedUser == null) {
            navController.navigate(Login) {
                popUpTo(Auth) { inclusive = true }
                launchSingleTop = true
            }
        } else {
            navController.navigate(Home) {
                popUpTo(Auth) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    items = listOf(
                        BottomNavItem("Home", Icons.Default.Home, Home::class.qualifiedName!!),
                        BottomNavItem("Weekly", Icons.Default.DateRange, Weekly::class.qualifiedName!!),
                        BottomNavItem("Search", Icons.Default.Search, Search::class.qualifiedName!!),
                        BottomNavItem("Profile", Icons.Default.Person, Profile::class.qualifiedName!!)
                    ),
                    navController = navController
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Auth,
            modifier = Modifier.padding(paddingValues)
        ) {
            navigation<Auth>(startDestination = Login) {
                composable<Login> {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Home) {
                                popUpTo(Auth) { inclusive = true }
                            }
                        },
                        onRegisterClick = {
                            navController.navigate(Register)
                        }
                    )
                }

                composable<Register> {
                    // RegistrationScreen(...)
                }
            }

            navigation<Main>(startDestination = Home) {
                composable<Home> {
                    HomeScreen()
                }
                composable<Search> {
                    SearchMealsScreen()
                }
                composable<Weekly> {
                    WeeklyPlannerScreen()
                }
                composable<Profile> {
                    ProfileScreen()
                }
            }
        }
    }
}