package com.example.meal_prep_planner_app.repository

import com.example.meal_prep_planner_app.dao.UserDao
import com.example.meal_prep_planner_app.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insert(entity: User) {
        userDao.insert(entity)
    }

    override suspend fun update(entity: User) {
        userDao.update(entity)
    }

    override suspend fun delete(entity: User) {
        userDao.delete(entity)
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

//    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
//        return userDao.getUserByEmailAndPassword(email, password)
//    }
}
