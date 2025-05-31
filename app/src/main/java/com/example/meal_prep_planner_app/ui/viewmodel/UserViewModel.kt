package com.example.meal_prep_planner_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meal_prep_planner_app.model.User
import com.example.meal_prep_planner_app.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.security.MessageDigest

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess: StateFlow<Boolean> = _registrationSuccess

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: StateFlow<Boolean?> = _loginStatus

    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> = _loggedUser

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            try {
                val hashedPassword = hashPassword(password)
                val user = User(email = email, password_hash = hashedPassword, fullName = name)
                userRepo.insert(user)
                _registrationSuccess.value = true
            } catch (e: Exception) {
                _error.value = "Registration failed: ${e.message}"
                Log.e("UserViewModel", "Registration error", e)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val hashedPassword = hashPassword(password)
                val user = userRepo.getUserByEmailAndPassword(email, hashedPassword)
                _loginStatus.value = user != null
                if (user != null) {
                    _loggedUser.value = user
                    Log.d("UserViewModel", "Logged in as: ${user.email}")
                } else {
                    _error.value = "Login failed: Invalid credentials"
                }
            } catch (e: Exception) {
                _error.value = "Login error: ${e.message}"
                Log.e("UserViewModel", "Login error", e)
            }
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}
