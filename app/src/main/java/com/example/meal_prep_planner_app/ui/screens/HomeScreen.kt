package com.example.meal_prep_planner_app.ui.screens

import RecipeCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meal_prep_planner_app.R
import com.example.meal_prep_planner_app.model.Recipe
import com.example.meal_prep_planner_app.ui.components.GreetingSection
import com.example.meal_prep_planner_app.ui.components.Meal
import com.example.meal_prep_planner_app.ui.components.MealCard

import com.example.meal_prep_planner_app.ui.viewmodel.RecipeViewModel


@Composable
fun HomeScreen(viewModel: RecipeViewModel = hiltViewModel()) {
    val popularRecipes by viewModel.recipes.collectAsState()
    val selectedRecipe = remember { mutableStateOf<Recipe?>(null) } // 👈 Add this

    LaunchedEffect(Unit) {
        viewModel.loadRecipes(null) // Or filter for most popular, if needed
    }

    // meals stays the same
    val meals = listOf(
        Meal("BREAKFAST", "8:00 AM", "Avocado toast with poached eggs"),
        Meal("LUNCH", "2:00 PM", "Grilled chicken salad"),
        Meal("DINNER", "7:00 PM", "Vegetable stir fry with tofu")
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 30.dp)
        ) {
            GreetingSection(name = "John")

            Text(
                text = "Today's overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            meals.forEach { meal ->
                MealCard(
                    meal = meal,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Text(
                text = "Popular recipes this week",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 25.dp, bottom = 8.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(popularRecipes) { recipe ->
                    RecipeCard(recipe = recipe) {
                        selectedRecipe.value = recipe
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))


            selectedRecipe.value?.let { recipe ->
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { selectedRecipe.value = null },
                    title = {
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    text = {
                        Column {
                            Text("Description: ${recipe.description}")
                            Text("Prep Time: ${recipe.prep_time} min")
                            Text("Cook Time: ${recipe.cook_time} min")
                            Text("Servings: ${recipe.servings}")
                            Text("Difficulty: ${recipe.difficulty}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Instructions:", fontWeight = FontWeight.Bold)
                            Text(recipe.instructions)
                        }
                    },
                    confirmButton = {
                        androidx.compose.material3.TextButton(onClick = { selectedRecipe.value = null }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}
