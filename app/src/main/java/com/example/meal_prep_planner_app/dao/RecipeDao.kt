package com.example.meal_prep_planner_app.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.meal_prep_planner_app.model.Recipe

@Dao
interface RecipeDao : BaseDao<Recipe> {

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId : Int): Recipe?

    @Query("SELECT * FROM recipes WHERE user_id IS NULL")
    suspend fun getPublicRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE user_id IS NULL OR user_id = :userId")
    suspend fun getAllRecipesForUser(userId: Int): List<Recipe>
}
