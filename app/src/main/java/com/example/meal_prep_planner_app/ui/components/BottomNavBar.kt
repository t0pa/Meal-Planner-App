package com.example.meal_prep_planner_app.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.meal_prep_planner_app.ui.theme.NavBarWhite
import com.example.meal_prep_planner_app.ui.theme.PastelGreen

@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Weekly Planner", Icons.Default.DateRange),
        BottomNavItem("Search Meals", Icons.Default.Search),
        BottomNavItem("Favorites", Icons.Default.Favorite)
    )

    NavigationBar(
        modifier = modifier.height(110.dp),
        containerColor = PastelGreen
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(30.dp),
                        tint = NavBarWhite
                    )
                },
                selected = item.title == "Home",
                onClick = { /* handle navigation */ },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavBarWhite,
                    unselectedIconColor = NavBarWhite.copy(alpha = 0.8f),
                    indicatorColor = Color(0xFF3A6055)
                )
            )
        }
    }

}

data class BottomNavItem(val title: String, val icon: ImageVector)