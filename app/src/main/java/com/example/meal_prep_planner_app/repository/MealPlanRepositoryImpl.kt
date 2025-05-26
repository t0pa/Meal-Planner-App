package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.dao.MealPlanDao
import com.example.meal_prep_planner_app.model.MealPlan
import javax.inject.Inject

class MealPlanRepositoryImpl @Inject constructor(
    private val mealPlanDao: MealPlanDao
) : MealPlanRepository {

    override suspend fun insert(entity: MealPlan) {
        mealPlanDao.insert(entity)
    }

    override suspend fun update(entity: MealPlan) {
        mealPlanDao.update(entity)
    }

    override suspend fun delete(entity: MealPlan) {
        mealPlanDao.delete(entity)
    }

    override suspend fun getMealPlanById(id: Int): MealPlan? {
        return mealPlanDao.getMealPlanById(id)
    }

    override suspend fun getMealPlansByUser(userId: Int): List<MealPlan> {
        return mealPlanDao.getMealPlansByUser(userId)
    }
}
