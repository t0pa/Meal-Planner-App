package com.example.meal_prep_planner_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun MealPrepPlannerAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = PrimaryColor,
            secondary = SecondaryColor,
            background = BackgroundColor
        ),
        content = content
    )
}