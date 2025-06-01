package com.example.meal_prep_planner_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.meal_prep_planner_app.dao.*
import com.example.meal_prep_planner_app.model.*

@Database(
    entities = [
        User::class,
        Recipe::class,
        MealPlan::class,
        RecipeIngredient::class,
        Ingredient::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao
    abstract fun ingredientDao(): IngredientDao
}

