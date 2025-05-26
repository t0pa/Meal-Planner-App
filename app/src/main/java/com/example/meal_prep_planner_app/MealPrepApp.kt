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
            //Test you can run here
            //database.userDao().insert(User(id = 1, email = "adnan.hajro@ibu.edu.ba", "SomePassword", "Adnan Hajro"))
            //dummy query on database just to open the connection
            database.userDao().getUserById(1)
            Log.d("DatabaseTest", "Database initialized")
        }
        }
}
