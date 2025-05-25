package com.example.meal_prep_planner_app.dao


import androidx.room.Dao
import androidx.room.Query
import com.example.meal_prep_planner_app.model.User


@Dao
interface UserDao : BaseDao<User> {
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}
