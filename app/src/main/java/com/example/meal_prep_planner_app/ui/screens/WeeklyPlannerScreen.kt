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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
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
    val loggedUserState = userViewModel.loggedUser.collectAsState()
    val loggedUser = loggedUserState.value

    val mealPlans by mealPlanViewModel.mealPlans.collectAsState()
    val error by mealPlanViewModel.error.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var dialogError by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(loggedUser?.id) {
        loggedUser?.id?.let {
            mealPlanViewModel.loadMealPlans(it)
            recipeViewModel.loadRecipes(loggedUser.id)
        }
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
            plan.date
        }
    }

    if (showDialog) {
        AddMealPlanDialog(
            onDismiss = { showDialog = false; dialogError = null },
            errorMessage = dialogError,
            recipeViewModel = recipeViewModel, // Add this
            onConfirm = { recipeName, mealType, date ->
                val recipe = recipeViewModel.recipes.value.find {
                    it.title.equals(recipeName, ignoreCase = true)
                }

                if (recipe != null && loggedUser != null) {
                    try {
                        LocalDate.parse(date)
                        val newPlan = MealPlan(
                            id = 0,
                            user_id = loggedUser.id,
                            recipe_id = recipe.id,
                            meal_type = mealType,
                            date = date
                        )
                        mealPlanViewModel.addMealPlan(newPlan)
                        showDialog = false
                        dialogError = null
                    } catch (e: Exception) {
                        dialogError = "Invalid date format. Use YYYY-MM-DD."
                    }
                } else {
                    dialogError = if (loggedUser == null) "User not logged in."
                    else "Recipe '$recipeName' not found."
                }
            }
        )
    }

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
                    key = { day -> day.date }
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
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text("Add meal plan")
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
    val mealTypeOrder = mapOf(
        "Breakfast" to 1,
        "Lunch" to 2,
        "Dinner" to 3,
    )

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
            val sortedPlans = mealPlans.sortedBy { mealTypeOrder[it.meal_type] ?: Int.MAX_VALUE }
            sortedPlans.forEach { plan ->
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

    val selectedRecipe by recipeViewModel.selectedRecipe.collectAsState()
    val recipe = selectedRecipe[mealPlan.recipe_id]

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
                text = recipe?.title ?: "Recipe #${mealPlan.recipe_id}",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSecondaryContainer
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMealPlanDialog(
    onDismiss: () -> Unit,
    onConfirm: (recipeName: String, mealType: String, date: String) -> Unit,
    errorMessage: String?,
    recipeViewModel: RecipeViewModel
) {
    var recipeName by remember { mutableStateOf("") }
    var mealType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }

    val allRecipes by recipeViewModel.recipes.collectAsState()
    var showRecipeDropdown by remember { mutableStateOf(false) }
    var showMealTypeDropdown by remember { mutableStateOf(false) }
    val mealTypes = listOf("Breakfast", "Lunch", "Dinner")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Meal Plan") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box {
                    OutlinedTextField(
                        value = recipeName,
                        onValueChange = { recipeName = it },
                        label = { Text("Recipe Name") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Show recipes",
                                modifier = Modifier.clickable { showRecipeDropdown = true }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = showRecipeDropdown,
                        onDismissRequest = { showRecipeDropdown = false }
                    ) {
                        allRecipes.filter {
                            it.title.contains(recipeName, ignoreCase = true)
                        }.forEach { recipe ->
                            DropdownMenuItem(
                                text = { Text(recipe.title) },
                                onClick = {
                                    recipeName = recipe.title
                                    showRecipeDropdown = false
                                }
                            )
                        }
                    }
                }

                Box {
                    OutlinedTextField(
                        value = mealType,
                        onValueChange = { mealType = it },
                        label = { Text("Meal Type") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Show meal types",
                                modifier = Modifier.clickable { showMealTypeDropdown = true }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = showMealTypeDropdown,
                        onDismissRequest = { showMealTypeDropdown = false }
                    ) {
                        mealTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    mealType = type
                                    showMealTypeDropdown = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            val isValid = recipeName.isNotBlank() && mealType.isNotBlank() && date.isNotBlank()
            TextButton(
                onClick = {
                    if (isValid) {
                        onConfirm(recipeName.trim(), mealType.trim(), date.trim())
                    }
                },
                enabled = isValid
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

