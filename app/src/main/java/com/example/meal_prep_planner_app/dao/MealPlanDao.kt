package com.example.meal_prep_planner_app.dao


import androidx.room.Dao
import androidx.room.Query
import com.example.meal_prep_planner_app.model.MealPlan

@Dao
interface MealPlanDao : BaseDao<MealPlan> {

    @Query("SELECT * FROM meal_plans WHERE id = :planId")
    suspend fun getMealPlanById(planId : Int): MealPlan?

    @Query("SELECT * FROM meal_plans WHERE user_id = :userId")
    suspend fun getMealPlansByUser(userId: Int): List<MealPlan>
}
