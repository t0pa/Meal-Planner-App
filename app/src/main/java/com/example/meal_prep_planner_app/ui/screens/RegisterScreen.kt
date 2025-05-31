package com.example.meal_prep_planner_app.ui.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import com.example.meal_prep_planner_app.R
import com.example.meal_prep_planner_app.ui.viewmodel.UserViewModel

@Composable
fun RegistrationScreen(userViewModel: UserViewModel, onLoginNav: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val registrationSuccess by userViewModel.registrationSuccess.collectAsState()
    val scrollState = rememberScrollState()

    val logo: Painter = painterResource(id = R.drawable.meal_prep_logo_green_removebg_preview)

    LaunchedEffect(registrationSuccess) {
        if(registrationSuccess){
            Toast.makeText(context, "You registered successfully!", Toast.LENGTH_SHORT).show()
            onLoginNav()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE0F2F7),
                        Color(0xFFC8E6C9)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = logo,
            contentDescription = "MealPrep Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
                .size(350.dp)
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(top = 250.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text("Register", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF388E3C))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text("Name", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color(0xFF388E3C),
                    focusedIndicatorColor = Color(0xFF388E3C),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = if (isValidEmail(it)) "" else "Please enter valid email address!"
                },
                label = { Text("Email", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color(0xFF388E3C),
                    focusedIndicatorColor = Color(0xFF388E3C),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Gray) },
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = "Toggle password visibility", tint = Color.Black)
                    }
                },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color(0xFF388E3C),
                    focusedIndicatorColor = Color(0xFF388E3C),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.Gray) },
                trailingIcon = {
                    val icon = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = "Toggle password visibility", tint = Color.Black)
                    }
                },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color(0xFF388E3C),
                    focusedIndicatorColor = Color(0xFF388E3C),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isBlank()) {
                        Toast.makeText(context, "Please enter your name!", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    } else if (email.isEmpty()) {
                        Toast.makeText(context, "Please enter your email!", Toast.LENGTH_SHORT).show()
                    }else if (password.isEmpty()) {
                        Toast.makeText(context, "Please enter a password!", Toast.LENGTH_SHORT).show()
                    }

                    userViewModel.register(email, password, name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                Text("Register", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onLoginNav) {
                Text("Already have an account? Login here", color = Color(0xFF1B5E20))
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}