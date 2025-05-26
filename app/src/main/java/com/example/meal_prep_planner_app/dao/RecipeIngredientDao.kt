package com.example.meal_prep_planner_app.dao


import androidx.room.Dao
import androidx.room.Query
import com.example.meal_prep_planner_app.model.RecipeIngredient

@Dao
interface RecipeIngredientDao : BaseDao<RecipeIngredient> {
    @Query("SELECT * FROM recipe_ingredients WHERE recipe_id = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Int): List<RecipeIngredient>
}
