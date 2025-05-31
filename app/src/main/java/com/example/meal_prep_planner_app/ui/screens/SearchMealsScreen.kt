package com.example.meal_prep_planner_app.ui.screens

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
import com.example.meal_prep_planner_app.ui.components.Recipe
import com.example.meal_prep_planner_app.ui.components.RecipeCard
import androidx.compose.foundation.lazy.grid.items


@Composable
fun SearchMealsScreen() {
    val searchQuery = remember { mutableStateOf("") }

    val colorScheme = MaterialTheme.colorScheme

    val allRecipes = remember {
        listOf(
            Recipe("Pasta Carbonara", R.drawable.pasta_sample),
            Recipe("Chicken Curry", R.drawable.curry_sample),
            Recipe("Greek Salad", R.drawable.salad_sample),
            Recipe("Beef Stroganoff", R.drawable.beef_stroganoff_sample),
            Recipe("Margherita Pizza", R.drawable.pizza_sample),
            Recipe("Sushi Platter", R.drawable.sushi_sample),
            Recipe("Tacos al Pastor", R.drawable.tacos_sample),
            Recipe("Tom Yum Soup", R.drawable.tom_yum_sample) //
        )
    }

    val filteredRecipes = if (searchQuery.value.isEmpty()) {
        allRecipes
    } else {
        allRecipes.filter {
            it.name.contains(searchQuery.value, ignoreCase = true)
        }
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

        // Search bar
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search for meals...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                items(filteredRecipes) { recipe ->
                    RecipeCard(
                        recipe = recipe
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}
