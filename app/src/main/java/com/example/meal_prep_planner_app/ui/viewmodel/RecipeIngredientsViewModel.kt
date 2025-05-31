package com.example.meal_prep_planner_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meal_prep_planner_app.model.RecipeIngredient
import com.example.meal_prep_planner_app.repository.RecipeIngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeIngredientViewModel @Inject constructor(
    private val recipeIngredientRepo: RecipeIngredientRepository
) : ViewModel() {

    private val _ingredients = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    val ingredients: StateFlow<List<RecipeIngredient>> = _ingredients

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadIngredientsForRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                _ingredients.value = recipeIngredientRepo.getIngredientsForRecipe(recipeId)
            } catch (e: Exception) {
                _error.value = "Failed to load ingredients: ${e.message}"
            }
        }
    }

    fun addIngredient(ingredient: RecipeIngredient) {
        viewModelScope.launch {
            try {
                recipeIngredientRepo.insert(ingredient)
                loadIngredientsForRecipe(ingredient.recipe_id)
            } catch (e: Exception) {
                _error.value = "Failed to add ingredient: ${e.message}"
            }
        }
    }

    fun updateIngredient(ingredient: RecipeIngredient) {
        viewModelScope.launch {
            try {
                recipeIngredientRepo.update(ingredient)
                loadIngredientsForRecipe(ingredient.recipe_id)
            } catch (e: Exception) {
                _error.value = "Failed to update ingredient: ${e.message}"
            }
        }
    }

    fun deleteIngredient(ingredient: RecipeIngredient) {
        viewModelScope.launch {
            try {
                recipeIngredientRepo.delete(ingredient)
                loadIngredientsForRecipe(ingredient.recipe_id)
            } catch (e: Exception) {
                _error.value = "Failed to delete ingredient: ${e.message}"
            }
        }
    }
}
