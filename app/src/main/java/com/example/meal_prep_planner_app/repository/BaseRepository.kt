package com.example.meal_prep_planner_app.repository

interface BaseRepository<T> {
    suspend fun insert(entity: T)
    suspend fun update(entity: T)
    suspend fun delete(entity: T)
}
