package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.model.User

interface UserRepository : BaseRepository<User> {
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

}
