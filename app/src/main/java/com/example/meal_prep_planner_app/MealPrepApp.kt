package com.example.meal_prep_planner_app

import com.example.meal_prep_planner_app.database.AppDatabase
import android.app.Application
import android.util.Log
import com.example.meal_prep_planner_app.model.MealPlan

import com.example.meal_prep_planner_app.model.Ingredient
import com.example.meal_prep_planner_app.model.Recipe
import com.example.meal_prep_planner_app.model.RecipeIngredient
import com.example.meal_prep_planner_app.model.User
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltAndroidApp
class       MealPrepApp : Application() {

    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
//
//            insertMockUsers()
//
//            insertMockIngredients()
//
//            insertMockRecipes()
//
//            insertMockMealPlans()
//
//            insertMockRecipeIngredients()
//


//            database.userDao().getUserById(1)
//            Log.d("DatabaseTest", "Database initialized")

        }
    }

    private fun hashPassword(password: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray())
            .joinToString("") { "%02x".format(it) }
    }

    private suspend fun insertMockUsers() {
        val users = listOf(
            User(1, "test@example.com", hashPassword("123"), "John Doe")
        )
        users.forEach { database.userDao().insert(it) }
    }

    private suspend fun insertMockRecipes() {
        val recipes = listOf(
            Recipe(1, "Pasta Carbonara", "Classic Italian dish.", 15, 20, 2, "Medium", "pasta_sample.jpg", null, "Boil pasta and mix with sauce."),
            Recipe(2, "Chicken Curry", "Spicy Indian curry.", 20, 30, 4, "Hard", "curry_sample.jpg", null, "Cook chicken with curry paste."),
            Recipe(3, "Greek Salad", "Fresh veggie salad.", 10, 0, 2, "Easy", "salad_sample.jpg", null, "Mix ingredients."),
            Recipe(4, "Beef Stroganoff", "Russian dish with beef.", 20, 25, 3, "Medium", "beef_stroganoff_sample.jpg", null, "Cook beef and mix with sauce."),
            Recipe(5, "Margherita Pizza", "Classic pizza.", 25, 15, 2, "Medium", "pizza_sample.jpg", null, "Bake pizza."),
            Recipe(6, "Sushi Platter", "Assorted sushi.", 40, 0, 4, "Hard", "sushi_sample.jpg", null, "Assemble sushi."),
            Recipe(7, "Tacos al Pastor", "Mexican tacos.", 20, 15, 3, "Medium", "tacos_sample.jpg", null, "Grill and fill tacos."),
            Recipe(8, "Tom Yum Soup", "Thai hot and sour soup.", 15, 25, 3, "Medium", "tom_yum_sample.jpg", null, "Boil ingredients.")
        )
        recipes.forEach { database.recipeDao().insert(it) }
    }

    private suspend fun insertMockIngredients() {
        val ingredients = listOf(
            Ingredient(1, "Spaghetti"), Ingredient(2, "Eggs"), Ingredient(3, "Parmesan Cheese"),
            Ingredient(4, "Halal Turkey Strips"), Ingredient(5, "Black Pepper"), Ingredient(6, "Halal Chicken"),
            Ingredient(7, "Curry Powder"), Ingredient(8, "Coconut Milk"), Ingredient(9, "Garlic"),
            Ingredient(10, "Onions"), Ingredient(11, "Lettuce"), Ingredient(12, "Feta Cheese"),
            Ingredient(13, "Cucumber"), Ingredient(14, "Tomatoes"), Ingredient(15, "Olives"),
            Ingredient(16, "Halal Beef"), Ingredient(17, "Mushrooms"), Ingredient(18, "Sour Cream"),
            Ingredient(19, "Butter"), Ingredient(20, "Pizza Dough"), Ingredient(21, "Tomato Sauce"),
            Ingredient(22, "Mozzarella"), Ingredient(23, "Basil"), Ingredient(24, "Olive Oil"),
            Ingredient(25, "Sushi Rice"), Ingredient(26, "Nori"), Ingredient(27, "Avocado"),
            Ingredient(28, "Cooked Shrimp"), Ingredient(29, "Halal Lamb"), Ingredient(30, "Tortillas"),
            Ingredient(31, "Pineapple"), Ingredient(32, "Cilantro"), Ingredient(33, "Lime"),
            Ingredient(34, "Lemongrass"), Ingredient(35, "Lime Juice"), Ingredient(36, "Chili Peppers"),
            Ingredient(37, "Mushrooms")
        )
        ingredients.forEach { database.ingredientDao().insert(it) }
    }

    private suspend fun insertMockRecipeIngredients() {
        val recipeIngredients = listOf(
            RecipeIngredient(1, 1, 1, 100, "g"), RecipeIngredient(2, 1, 2, 2, "pcs"),
            RecipeIngredient(3, 1, 3, 50, "g"), RecipeIngredient(4, 1, 4, 100, "g"),
            RecipeIngredient(5, 2, 6, 300, "g"), RecipeIngredient(6, 2, 7, 20, "g"),
            RecipeIngredient(7, 2, 8, 200, "ml"), RecipeIngredient(8, 2, 9, 2, "cloves"),
            RecipeIngredient(9, 3, 11, 100, "g"), RecipeIngredient(10, 3, 12, 50, "g"),
            RecipeIngredient(11, 3, 13, 1, "pcs"), RecipeIngredient(12, 3, 14, 2, "pcs"),
            RecipeIngredient(13, 4, 16, 250, "g"), RecipeIngredient(14, 4, 17, 100, "g"),
            RecipeIngredient(15, 4, 18, 50, "ml"), RecipeIngredient(16, 5, 20, 200, "g"),
            RecipeIngredient(17, 5, 21, 50, "ml"), RecipeIngredient(18, 5, 22, 100, "g"),
            RecipeIngredient(19, 6, 25, 150, "g"), RecipeIngredient(20, 6, 26, 3, "sheets"),
            RecipeIngredient(21, 6, 27, 1, "pcs"), RecipeIngredient(22, 6, 28, 100, "g"),
            RecipeIngredient(23, 7, 29, 200, "g"), RecipeIngredient(24, 7, 30, 3, "pcs"),
            RecipeIngredient(25, 7, 31, 50, "g"), RecipeIngredient(26, 8, 36, 100, "g"),
            RecipeIngredient(27, 8, 34, 2, "stalks"), RecipeIngredient(28, 8, 35, 30, "ml"),
            RecipeIngredient(29, 8, 36, 2, "pcs")
        )
        recipeIngredients.forEach { database.recipeIngredientDao().insert(it) }
    }

    private suspend fun insertMockMealPlans() {
        val mealPlans = listOf(
            MealPlan(1, 1, 1, "Lunch", "2025-06-10"), MealPlan(2, 1, 3, "Dinner", "2025-06-10"),
            MealPlan(3, 1, 2, "Lunch", "2025-06-11"), MealPlan(4, 1, 4, "Dinner", "2025-06-12"),
            MealPlan(5, 1, 5, "Lunch", "2025-06-13"), MealPlan(6, 1, 6, "Dinner", "2025-06-13"),
            MealPlan(7, 1, 3, "Lunch", "2025-06-14"), MealPlan(8, 1, 7, "Dinner", "2025-06-16"),
            MealPlan(9, 1, 8, "Lunch", "2025-06-17"), MealPlan(10, 1, 1, "Dinner", "2025-06-18"),
            MealPlan(11, 1, 5, "Breakfast", "2025-06-19"), MealPlan(12, 1, 6, "Lunch", "2025-06-19"),
            MealPlan(13, 1, 4, "Dinner", "2025-06-20"), MealPlan(14, 1, 2, "Lunch", "2025-06-21")
        )
        mealPlans.forEach { database.mealPlanDao().insert(it) }
    }

}
