package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.dao.IngredientDao
import com.example.meal_prep_planner_app.model.Ingredient
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao
) : IngredientRepository {

    override suspend fun insert(entity: Ingredient) {
        ingredientDao.insert(entity)
    }

    override suspend fun update(entity: Ingredient) {
        ingredientDao.update(entity)
    }

    override suspend fun delete(entity: Ingredient) {
        ingredientDao.delete(entity)
    }

    override suspend fun getAllIngredients(): List<Ingredient> {
        return ingredientDao.getAllIngredients()
    }

//    override suspend fun getIngredientById(id: Int): Ingredient? {
//        return ingredientDao.getIngredientById(id)
//    }
}
