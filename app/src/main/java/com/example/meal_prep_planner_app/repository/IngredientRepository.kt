package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.model.Ingredient

interface IngredientRepository : BaseRepository<Ingredient> {
    suspend fun getAllIngredients(): List<Ingredient>
    //suspend fun getIngredientById(id: Int): Ingredient?
}
