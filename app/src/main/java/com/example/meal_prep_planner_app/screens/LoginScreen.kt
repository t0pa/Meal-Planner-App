package com.example.meal_prep_planner_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.painter.Painter
import com.example.meal_prep_planner_app.R

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegisterClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Load your logo as a painter
    val logo: Painter = painterResource(id = R.drawable.meal_prep_logo_green_removebg_preview)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE0F2F7), // Light greenish-blue
                        Color(0xFFC8E6C9)  // Light green
                    )
                )
            ),
        contentAlignment = Alignment.Center // This centers the Column
    ) {
        // Logo on a separate layer, positioned at the top center

            Image(
                painter = logo,
                contentDescription = "MealPrep Logo",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp) // Adjust top padding as needed
                    .size(350.dp) // Adjust size as needed
            )


        // Column containing the login form elements
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp) // Add horizontal padding
                .padding(top = 200.dp) // Push the column down below the logo (adjust as needed)
                .fillMaxWidth(), // Make the column take full width
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Text("Login", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF388E3C)) // Dark green


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
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
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
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
                    // Add authentication logic here
                    if (email.isNotBlank() && password.isNotBlank()) {
                        onLoginSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)) // Dark green
            ) {
                Text("Login", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onRegisterClick) {
                Text("Don't have an account? Register here", color = Color(0xFF1B5E20)) // Darker green
            }
        }
    }
}