package com.example.meal_prep_planner_app.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class DayItem(
    val dayOfWeek: String,
    val date: Int,
    val isToday: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyPlannerScreen(
    onAddMealClick: () -> Unit = {},
    onDaySlotClick: (day: String, date: Int) -> Unit = { _, _ -> }
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var currentWeek by remember { mutableStateOf(LocalDate.now()) }

    // calculate week range
    val weekStart = currentWeek.with(DayOfWeek.MONDAY)
    val weekEnd = weekStart.plusDays(6)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM")

    // generate days for the week
    val weekDays = remember(currentWeek) {
        (0..6).map { dayOffset ->
            val date = weekStart.plusDays(dayOffset.toLong())
            DayItem(
                dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).first().toString(),
                date = date.dayOfMonth,
                isToday = date.isEqual(LocalDate.now())
            )
        }
    }

    // MOCK DATA FOR MEALS
    val mockMealPlansPerDay = mapOf(
        "M" to listOf(
            MealPlan("Breakfast", "Oatmeal"),
            MealPlan("Brunch", "Avocado Toast"),
            MealPlan("Lunch", "Grilled Chicken"),
            MealPlan("Dinner", "Pasta")
        ),
        "T" to listOf(
            MealPlan("Breakfast", "Eggs"),
            MealPlan("Lunch", "Burger"),
            MealPlan("Dinner", "Salad")
        ),
        "W" to listOf(), // Empty
        "T" to listOf(
            MealPlan("Breakfast", "Smoothie", isEmpty = false),
            MealPlan("Lunch", "Soup", isEmpty = false)
        ),
        "F" to listOf(), // Empty
        "S" to listOf(
            MealPlan("Breakfast", "Pancakes"),
            MealPlan("Lunch", "Pizza")
        ),
        "S" to listOf() // Empty
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Weekly Planner",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // week navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { currentWeek = currentWeek.minusWeeks(1) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous week",
                        tint = colorScheme.primary
                    )
                }

                Text(
                    text = "${weekStart.format(dateFormatter)} - ${weekEnd.format(dateFormatter)}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )

                IconButton(
                    onClick = { currentWeek = currentWeek.plusWeeks(1) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next week",
                        tint = colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // week calendar grid
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {
                items(
                    weekDays.size
                ) { index ->
                    val dayItem = weekDays[index]
                    DayColumn(
                        dayItem = dayItem,
                        mealPlans = mockMealPlansPerDay[dayItem.dayOfWeek] ?: emptyList(),
                        onSlotClick = { onDaySlotClick(dayItem.dayOfWeek, dayItem.date) },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(horizontal = 4.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // add meal button
        Button(
            onClick = onAddMealClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text("Add meal plan", fontSize = 16.sp)
        }
    }
}

@Composable
private fun DayColumn(
    dayItem: DayItem,
    mealPlans: List<MealPlan> = emptyList(),
    onSlotClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayItem.dayOfWeek,
            fontWeight = FontWeight.Bold,
            color = if (dayItem.isToday) colorScheme.primary
                else colorScheme.onBackground.copy(alpha = 0.8f)
        )

        Text(
            text = dayItem.date.toString(),
            fontSize = 12.sp,
            color = if (dayItem.isToday) colorScheme.primary
                else colorScheme.onBackground.copy(alpha = 0.8f)
        )

        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            mealPlans.forEach { plan ->
                MealSlot(
                    mealPlan = plan,
                    onClick = onSlotClick,
                    modifier = Modifier.height(100.dp)
                )
            }

            if(mealPlans.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(80.dp)
                        .background(
                            color = colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = colorScheme.outline.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        "Empty",
                        color = colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

data class MealPlan(
    val mealType: String,
    val name: String,
    val isEmpty: Boolean = false
)

@Composable
fun MealSlot(
    mealPlan: MealPlan,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (mealPlan.isEmpty)
                    colorScheme.surfaceVariant.copy(alpha = 0.3f)
                else
                    colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (mealPlan.isEmpty)
                    colorScheme.outline.copy(alpha = 0.5f)
                else
                    colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                mealPlan.mealType,
                style = MaterialTheme.typography.labelMedium,
                color = colorScheme.onSecondaryContainer
            )
            if (!mealPlan.isEmpty) {
                Text(
                    mealPlan.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSecondaryContainer
                )
            }
        }
    }
}