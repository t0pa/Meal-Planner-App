package com.example.meal_prep_planner_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meal_prep_planner_app.R
import com.example.meal_prep_planner_app.ui.components.BottomNavBar
import com.example.meal_prep_planner_app.ui.components.GreetingSection
import com.example.meal_prep_planner_app.ui.components.Meal
import com.example.meal_prep_planner_app.ui.components.MealCard
import com.example.meal_prep_planner_app.ui.components.PopularRecipeCard
import com.example.meal_prep_planner_app.ui.components.Recipe

@Composable
fun HomeScreen() {
    val meals = listOf(
        Meal("BREAKFAST", "8:00 AM", "Avocado toast with poached eggs"),
        Meal("LUNCH", "2:00 PM", "Grilled chicken salad"),
        Meal("DINNER", "7:00 PM", "Vegetable stir fry with tofu")
    )

    val popularRecipes = listOf(
        Recipe("Pasta Carbonara", R.drawable.pasta_sample),
        Recipe("Chicken Curry", R.drawable.curry_sample),
        Recipe("Greek Salad", R.drawable.salad_sample),
        Recipe("Beef Stroganoff", R.drawable.beef_stroganoff_sample),
        Recipe("Margherita Pizza", R.drawable.pizza_sample),
        Recipe("Sushi Platter", R.drawable.sushi_sample),
        Recipe("Tacos al Pastor", R.drawable.tacos_sample),
        Recipe("Tom Yum Soup", R.drawable.tom_yum_sample)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 60.dp)
        ) {
            GreetingSection(name = "John")

            Text(
                text = "Today's overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp
                )
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
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 25.dp,
                    bottom = 8.dp
                )
            )

            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(popularRecipes) { recipe ->
                    PopularRecipeCard(recipe = recipe)
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }

        BottomNavBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}