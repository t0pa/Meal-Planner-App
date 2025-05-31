package com.example.meal_prep_planner_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meal_prep_planner_app.model.MealPlan
import com.example.meal_prep_planner_app.repository.MealPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanViewModel @Inject constructor(
    private val mealPlanRepo: MealPlanRepository
) : ViewModel() {

    private val _mealPlans = MutableStateFlow<List<MealPlan>>(emptyList())
    val mealPlans: StateFlow<List<MealPlan>> = _mealPlans

    private val _selectedMealPlan = MutableStateFlow<MealPlan?>(null)
    val selectedMealPlan: StateFlow<MealPlan?> = _selectedMealPlan

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadMealPlans(userId: Int) {
        viewModelScope.launch {
            try {
                _mealPlans.value = mealPlanRepo.getMealPlansByUser(userId)
            } catch (e: Exception) {
                _error.value = "Failed to load meal plans: ${e.message}"
            }
        }
    }

    fun getMealPlanById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedMealPlan.value = mealPlanRepo.getMealPlanById(id)
            } catch (e: Exception) {
                _error.value = "Failed to load meal plan: ${e.message}"
            }
        }
    }

    fun addMealPlan(mealPlan: MealPlan) {
        viewModelScope.launch {
            try {
                mealPlanRepo.insert(mealPlan)
                loadMealPlans(mealPlan.user_id)
            } catch (e: Exception) {
                _error.value = "Failed to add meal plan: ${e.message}"
            }
        }
    }

    fun updateMealPlan(mealPlan: MealPlan) {
        viewModelScope.launch {
            try {
                mealPlanRepo.update(mealPlan)
                loadMealPlans(mealPlan.user_id)
            } catch (e: Exception) {
                _error.value = "Failed to update meal plan: ${e.message}"
            }
        }
    }

    fun deleteMealPlan(mealPlan: MealPlan) {
        viewModelScope.launch {
            try {
                mealPlanRepo.delete(mealPlan)
                loadMealPlans(mealPlan.user_id)
            } catch (e: Exception) {
                _error.value = "Failed to delete meal plan: ${e.message}"
            }
        }
    }
}
