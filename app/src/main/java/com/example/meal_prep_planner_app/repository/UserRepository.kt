package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.model.User

interface UserRepository : BaseRepository<User> {
    suspend fun getAllUsers(): List<User>?
    suspend fun getUserByEmail(email: String): User?
   // suspend fun getUserByEmailAndPassword(email: String, password: String): User?
}
