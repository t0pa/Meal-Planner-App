package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.model.RecipeIngredient

interface RecipeIngredientRepository : BaseRepository<RecipeIngredient> {
    suspend fun getIngredientsForRecipe(recipeId: Int): List<RecipeIngredient>
}
