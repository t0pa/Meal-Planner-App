package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.dao.RecipeDao
import com.example.meal_prep_planner_app.model.Recipe
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override suspend fun insert(entity: Recipe) {
        recipeDao.insert(entity)
    }

    override suspend fun update(entity: Recipe) {
        recipeDao.update(entity)
    }

    override suspend fun delete(entity: Recipe) {
        recipeDao.delete(entity)
    }

    override suspend fun getAllRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes()
    }

//    override suspend fun getRecipeById(id: Int): Recipe? {
//        return recipeDao.getRecipeById(id)
//    }
}
