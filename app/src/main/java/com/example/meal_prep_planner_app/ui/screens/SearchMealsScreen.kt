package com.example.meal_prep_planner_app.ui.screens

import RecipeCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.example.meal_prep_planner_app.R
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meal_prep_planner_app.model.Recipe
import com.example.meal_prep_planner_app.ui.viewmodel.RecipeViewModel

@Composable
fun SearchMealsScreen(viewModel: RecipeViewModel = hiltViewModel()) {
    val searchQuery = remember { mutableStateOf("") }
    val recipes by viewModel.recipes.collectAsState()
    val colorScheme = MaterialTheme.colorScheme
    val selectedRecipe = remember { mutableStateOf<Recipe?>(null) } // 👈 Add this

    val filteredRecipes = recipes.filter {
        it.title.contains(searchQuery.value, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.loadRecipes(null) // or pass userId if needed
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Search Recipes",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 15.dp, bottom = 8.dp)
        )

        Text(
            text = "Find your next meal",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search for meals...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorScheme.primary,
                unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f),
                focusedContainerColor = colorScheme.secondaryContainer,
                unfocusedContainerColor = colorScheme.secondaryContainer
            ),
            singleLine = true
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredRecipes) { recipe ->
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
