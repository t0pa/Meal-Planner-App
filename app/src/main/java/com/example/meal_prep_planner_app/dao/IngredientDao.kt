package com.example.meal_prep_planner_app.dao


import androidx.room.Dao
import androidx.room.Query
import com.example.meal_prep_planner_app.model.Ingredient

@Dao
interface IngredientDao : BaseDao<Ingredient> {
    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    suspend fun getIngredientById(ingredientId : Int): Ingredient?
}
