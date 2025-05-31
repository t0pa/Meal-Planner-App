package com.example.meal_prep_planner_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meal_prep_planner_app.model.Recipe
import com.example.meal_prep_planner_app.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepo: RecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // load recipes for a specific user (or null for public only)
    fun loadRecipes(userId: Int?) {
        viewModelScope.launch {
            try {
                val result = if (userId != null) {
                    recipeRepo.getAllRecipesForUser(userId)
                } else {
                    recipeRepo.getPublicRecipes()
                }
                _recipes.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load recipes: ${e.message}"
            }
        }
    }

    fun getRecipeById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedRecipe.value = recipeRepo.getRecipeById(id)
            } catch (e: Exception) {
                _error.value = "Failed to load recipe: ${e.message}"
            }
        }
    }

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                recipeRepo.insert(recipe)
                loadRecipes(recipe.user_id) // reload user's recipes
            } catch (e: Exception) {
                _error.value = "Failed to add recipe: ${e.message}"
            }
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                recipeRepo.update(recipe)
                loadRecipes(recipe.user_id)
            } catch (e: Exception) {
                _error.value = "Failed to update recipe: ${e.message}"
            }
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                recipeRepo.delete(recipe)
                loadRecipes(recipe.user_id)
            } catch (e: Exception) {
                _error.value = "Failed to delete recipe: ${e.message}"
            }
        }
    }
}
