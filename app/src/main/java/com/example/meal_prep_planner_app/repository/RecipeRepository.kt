package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.model.Recipe

interface RecipeRepository : BaseRepository<Recipe> {
    suspend fun getAllRecipesForUser(userId: Int): List<Recipe>
    suspend fun getRecipeById(id: Int): Recipe?
    suspend fun getPublicRecipes(): List<Recipe>

}
