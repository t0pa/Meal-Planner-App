package com.example.meal_prep_planner_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meal_prep_planner_app.ui.theme.PrimaryColor
import com.example.meal_prep_planner_app.ui.theme.SecondaryColor

@Composable
fun MealCard (meal : Meal, modifier : Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor.copy(alpha = 0.1f)
        )
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = meal.name, fontWeight = FontWeight.Bold)
                Text(text = meal.time, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = meal.info,
                color = SecondaryColor,
                fontSize = 12.sp
            )
        }
    }
}

data class Meal(val name: String, val time: String, val info: String)