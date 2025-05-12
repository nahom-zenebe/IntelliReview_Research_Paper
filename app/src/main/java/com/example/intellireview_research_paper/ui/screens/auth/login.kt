// File: ui/screens/LoginScreen.kt
package com.example.intellireview_research_paper.ui.screens

import UserViewModel
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.data.mapper.UserRepositoryImpl

@Composable
fun LoginScreen(
    navController: NavController,
    userRepository: UserRepositoryImpl,
    onBackClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    class UserViewModelFactory(private val repo: UserRepositoryImpl) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepository))
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage
    val user = viewModel.user
    val context = LocalContext.current

    LaunchedEffect(user) {
        user?.let {
            // Synchronous write so profile sees it immediately
            val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            prefs.edit()
                .putString("KEY_NAME",  it.name)
                .putString("KEY_EMAIL", it.email)
                .putString("KEY_ROLE",  it.role)
                .apply()     // <-- COMMIT instead of apply()

            Log.d("SharedPrefs", "Saved: ${user.name}, ${user.email}, ${user.role}")

            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
            // Check the role and navigate to the appropriate screen
            if (user.role == "admin") {
                navController.navigate("admindashboard") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearStatus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.research_paper),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(top = 20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = onBackClick, Modifier.align(Alignment.Start)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xff36454f),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Email section
            Text(
                "Email",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xff36454f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { 
                    email = it
                    emailError = if (it.isBlank()) "Email cannot be empty" 
                               else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Invalid email format"
                               else null
                },
                placeholder = { Text("johndoe@example.com") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFD3D3D3),
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it, color = Color.Red) } }
            )
            Spacer(Modifier.height(16.dp))

            // Password section
            Text(
                "Password",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xff36454f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    passwordError = if (it.isBlank()) "Password cannot be empty"
                                  else if (it.length < 6) "Password must be at least 6 characters"
                                  else null
                },
                placeholder = { Text("••••••••") },
                singleLine = true,
                visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible)
                        Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFD3D3D3),
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                isError = passwordError != null,
                supportingText = { passwordError?.let { Text(it, color = Color.Red) } }
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { 
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    } else if (emailError == null && passwordError == null) {
                        viewModel.login(email, password)
                    } else {
                        Toast.makeText(context, "Please fix the validation errors", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D5CBB))
            ) {
                Text(
                    if (isLoading) "Logging in..." else "Login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))
            HorizontalDivider(color = Color.Gray, modifier = Modifier.fillMaxWidth(0.8f))
            Spacer(Modifier.height(8.dp))

            Text(
                buildAnnotatedString {
                    append("No account? ")
                    withStyle(SpanStyle(color = Color(0xFF5D5CBB), fontWeight = FontWeight.Bold)) {
                        append("Sign up")
                    }
                },
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable { navController.navigate("signup") }
                    .padding(vertical = 16.dp)
            )
        }
    }
}
