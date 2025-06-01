package com.example.meal_prep_planner_app

import com.example.meal_prep_planner_app.database.AppDatabase
import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class       MealPrepApp : Application() {

    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {

            database.userDao().getUserById(1)
            Log.d("DatabaseTest", "Database initialized")

        }
    }

}
