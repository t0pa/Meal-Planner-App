package com.example.meal_prep_planner_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meal_prep_planner_app.model.Recipe
import com.example.meal_prep_planner_app.model.RecipeIngredient
import com.example.meal_prep_planner_app.repository.RecipeIngredientRepository
import com.example.meal_prep_planner_app.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val recipeIngredientRepo: RecipeIngredientRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _selectedRecipe = MutableStateFlow<Map<Int, Recipe>>(emptyMap())
    val selectedRecipe: StateFlow<Map<Int, Recipe>> = _selectedRecipe

    private val _ingredients = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    val ingredients: StateFlow<List<RecipeIngredient>> = _ingredients

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // load recipes (optionally for a specific user)
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
            if (_selectedRecipe.value.containsKey(id)) return@launch // already cached
            try {
                val recipe = recipeRepo.getRecipeById(id)
                _selectedRecipe.value = (_selectedRecipe.value + (id to recipe)) as Map<Int, Recipe>
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

    // RECIPE INGREDIENTS

    fun loadIngredientsForRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                _ingredients.value = recipeIngredientRepo.getIngredientsForRecipe(recipeId)
            } catch (e: Exception) {
                _error.value = "Failed to load ingredients: ${e.message}"
            }
        }
    }

    fun addIngredientToRecipe(ingredient: RecipeIngredient) {
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
