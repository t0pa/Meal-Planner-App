package com.example.meal_prep_planner_app.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.meal_prep_planner_app.model.Recipe

@Dao
interface RecipeDao : BaseDao<Recipe> {


    @Query("SELECT * FROM recipes WHERE user_id = :userId")
    suspend fun getRecipesByUser(userId: Int): List<Recipe>

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>
}
