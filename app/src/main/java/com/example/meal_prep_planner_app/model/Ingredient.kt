package com.example.meal_prep_planner_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey val id: Int,
    val name: String
)
