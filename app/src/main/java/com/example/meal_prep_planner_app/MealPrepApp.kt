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
class MealPrepApp : Application() {

    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        // Optional: trigger a no-op DB query to ensure DB is initialized
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Example: Test DB access (replace with your DAO method)
                val allUsers = database.userDao().getAllUsers()
                Log.d("DatabaseTest", "App launched, found ${allUsers.size} users.")
            } catch (e: Exception) {
                Log.e("DatabaseTest", "Error initializing DB: ${e.message}")
            }
        }
    }
}
