package com.example.meal_prep_planner_app.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("user_id")]
)
data class Recipe(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val prep_time: Int,
    val cook_time: Int,
    val servings: Int,
    val difficulty: String,
    val category: Int,
    val image_url: String,
    val user_id: Int,
    val instructions: String
)
