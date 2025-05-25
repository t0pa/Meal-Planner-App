package com.example.meal_prep_planner_app.database

import androidx.room.Database

@Database(
    entities = [
        User::class,
        Recipe::class,
        MealPlan::class,
        RecipeIngredient::class,
        Ingredient::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao
    abstract fun ingredientDao(): IngredientDao
}

