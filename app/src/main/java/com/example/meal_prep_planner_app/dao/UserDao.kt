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

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

}
