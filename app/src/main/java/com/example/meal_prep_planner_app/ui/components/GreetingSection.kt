package com.example.meal_prep_planner_app.ui.components

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GreetingSection(
    name : String,
    modifier : Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Column (
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Hello $name",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary
        )
        Text(
            text = "What would you like to cook today?",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}