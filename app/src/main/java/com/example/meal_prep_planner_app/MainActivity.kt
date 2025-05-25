package com.example.meal_prep_planner_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.meal_prep_planner_app.ui.theme.MealPrepPlannerAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealPrepPlannerAppTheme {
                AppNavigation()
            }
        }
    }
}