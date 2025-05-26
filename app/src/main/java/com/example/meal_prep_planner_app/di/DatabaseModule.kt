package com.example.meal_prep_planner_app.di

import android.content.Context
import androidx.room.Room
import com.example.meal_prep_planner_app.dao.*
import com.example.meal_prep_planner_app.database.AppDatabase
import com.example.meal_prep_planner_app.repository.IngredientRepository
import com.example.meal_prep_planner_app.repository.IngredientRepositoryImpl
import com.example.meal_prep_planner_app.repository.MealPlanRepository
import com.example.meal_prep_planner_app.repository.MealPlanRepositoryImpl
import com.example.meal_prep_planner_app.repository.RecipeIngredientRepository
import com.example.meal_prep_planner_app.repository.RecipeIngredientRepositoryImpl
import com.example.meal_prep_planner_app.repository.RecipeRepository
import com.example.meal_prep_planner_app.repository.RecipeRepositoryImpl
import com.example.meal_prep_planner_app.repository.UserRepository
import com.example.meal_prep_planner_app.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "meal_prep_planner.db"
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao = appDatabase.recipeDao()

    @Provides
    fun provideMealPlanDao(appDatabase: AppDatabase): MealPlanDao = appDatabase.mealPlanDao()

    @Provides
    fun provideIngredientDao(appDatabase: AppDatabase): IngredientDao = appDatabase.ingredientDao()

    @Provides
    fun provideRecipeIngredientDao(appDatabase: AppDatabase): RecipeIngredientDao = appDatabase.recipeIngredientDao()


    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao
    ): UserRepository = UserRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDao: RecipeDao
    ): RecipeRepository = RecipeRepositoryImpl(recipeDao)

    @Provides
    @Singleton
    fun provideMealPlanRepository(
        mealPlanDao: MealPlanDao
    ): MealPlanRepository = MealPlanRepositoryImpl(mealPlanDao)

    @Provides
    @Singleton
    fun provideIngredientRepository(
        ingredientDao: IngredientDao
    ): IngredientRepository = IngredientRepositoryImpl(ingredientDao)

    @Provides
    @Singleton
    fun provideRecipeIngredientRepository(
        recipeIngredientDao: RecipeIngredientDao
    ): RecipeIngredientRepository = RecipeIngredientRepositoryImpl(recipeIngredientDao)
}
