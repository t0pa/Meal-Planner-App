package com.example.meal_prep_planner_app.ui.screens

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meal_prep_planner_app.ui.viewmodel.MealPlanViewModel
import com.example.meal_prep_planner_app.ui.viewmodel.UserViewModel
import com.example.meal_prep_planner_app.model.MealPlan
import com.example.meal_prep_planner_app.ui.viewmodel.RecipeViewModel
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
    mealPlanViewModel: MealPlanViewModel = hiltViewModel(),
    recipeViewModel: RecipeViewModel = hiltViewModel(),

    userViewModel : UserViewModel,
    onAddMealClick: () -> Unit = {},
    onDaySlotClick: (day: String, date: Int) -> Unit = { _, _ -> }
){
    val loggedUser by userViewModel.loggedUser.collectAsState()
    val mealPlans by mealPlanViewModel.mealPlans.collectAsState()
    val error by mealPlanViewModel.error.collectAsState()

    LaunchedEffect(loggedUser?.id) {
        loggedUser?.id?.let { mealPlanViewModel.loadMealPlans(it) }
    }

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
    val mealPlansPerDay = remember(mealPlans) {
        mealPlans.groupBy { plan ->
            plan.date  // group by the full date string, e.g. "2025-06-01"
        }
    }

//    // MOCK DATA FOR MEALS
//    val mockMealPlansPerDay = mapOf(
//        "M" to listOf(
//            MealPlan("Breakfast", "Oatmeal"),
//            MealPlan("Brunch", "Avocado Toast"),
//            MealPlan("Lunch", "Grilled Chicken"),
//            MealPlan("Dinner", "Pasta")
//        ),
//        "T" to listOf(
//            MealPlan("Breakfast", "Eggs"),
//            MealPlan("Lunch", "Burger"),
//            MealPlan("Dinner", "Salad")
//        ),
//        "W" to listOf(), // Empty
//        "T" to listOf(
//            MealPlan("Breakfast", "Smoothie", isEmpty = false),
//            MealPlan("Lunch", "Soup", isEmpty = false)
//        ),
//        "F" to listOf(), // Empty
//        "S" to listOf(
//            MealPlan("Breakfast", "Pancakes"),
//            MealPlan("Lunch", "Pizza")
//        ),
//        "S" to listOf() // Empty
//    )


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
                    items = weekDays,
                    key = { day -> day.date } // Or a more stable key like day.dayOfWeek + date string
                ) { dayItem ->
                    DayColumn(
                        dayItem = dayItem,
                        mealPlans = mealPlansPerDay[weekStart.plusDays(weekDays.indexOf(dayItem).toLong()).toString()] ?: emptyList(),
                        onSlotClick = { onDaySlotClick(dayItem.dayOfWeek, dayItem.date) },
                        recipeViewModel = recipeViewModel,
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
    recipeViewModel: RecipeViewModel,
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
                    recipeViewModel = recipeViewModel,
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


@Composable
fun MealSlot(
    mealPlan: MealPlan,
    recipeViewModel: RecipeViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    // Observe selected recipe state for this recipe id
    val selectedRecipe by recipeViewModel.selectedRecipe.collectAsState()

    // Trigger loading recipe on first composition or when mealPlan changes
    LaunchedEffect(mealPlan.recipe_id) {
        recipeViewModel.getRecipeById(mealPlan.recipe_id)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                mealPlan.meal_type,
                style = MaterialTheme.typography.labelMedium,
                color = colorScheme.onSecondaryContainer
            )
            Text(
                text = selectedRecipe?.title ?: "Recipe #${mealPlan.recipe_id}",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSecondaryContainer
            )
        }
    }
}

