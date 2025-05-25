package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.model.MealPlan

interface MealPlanRepository : BaseRepository<MealPlan> {
    //suspend fun getMealPlanById(id: Int): MealPlan?
    suspend fun getMealPlansByUser(userId: Int): List<MealPlan>
}
