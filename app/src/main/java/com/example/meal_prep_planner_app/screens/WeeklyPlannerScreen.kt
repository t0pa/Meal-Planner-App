package com.example.meal_prep_planner_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeeklyPlannerScreen() {
    val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")
    val dates = listOf(10, 11, 12, 13, 14, 15, 16)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Weekly Planner", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // week range with arrows
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) { // go to previous week
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous week")
            }
            Text("10.04 - 16.04", fontSize = 16.sp)
            IconButton(onClick = {}) { // go to next week
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next week")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // week days
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                daysOfWeek.zip(dates).forEachIndexed { index, (day, date) ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(day, fontWeight = FontWeight.Bold)
                        Text(date.toString(), fontSize = 12.sp)

                        Column(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .height(150.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            repeat((0..2).random()) {
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .background(
                                            Color(0xFFB2DFDB),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable {} // need to handle
                                )
                            }
                        }
                    }

                    // vertical lines
                    if (index < daysOfWeek.lastIndex) {
                        Box(
                            modifier = Modifier
                                .height(500.dp)
                                .width(1.dp)
                                .background(Color.LightGray.copy(alpha = 0.5f))
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(24.dp))

        // button to add meals
        Button(
            onClick = {}, // open screen to add meal plan
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Add meal plan")
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
