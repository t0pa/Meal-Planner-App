package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.dao.RecipeIngredientDao
import com.example.meal_prep_planner_app.model.RecipeIngredient
import javax.inject.Inject

class RecipeIngredientRepositoryImpl @Inject constructor(
    private val recipeIngredientDao: RecipeIngredientDao
) : RecipeIngredientRepository {

    override suspend fun insert(entity: RecipeIngredient) {
        recipeIngredientDao.insert(entity)
    }

    override suspend fun update(entity: RecipeIngredient) {
        recipeIngredientDao.update(entity)
    }

    override suspend fun delete(entity: RecipeIngredient) {
        recipeIngredientDao.delete(entity)
    }

    override suspend fun getIngredientsForRecipe(recipeId: Int): List<RecipeIngredient> {
        return recipeIngredientDao.getIngredientsForRecipe(recipeId)
    }
}
