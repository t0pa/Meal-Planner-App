package com.example.meal_prep_planner_app.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe_ingredients",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["ingredient_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("recipe_id"), Index("ingredient_id")]
)
data class RecipeIngredient(
    @PrimaryKey val id: Int,
    val recipe_id: Int,
    val ingredient_id: Int,
    val quantity: Int,
    val unit: Int
)
