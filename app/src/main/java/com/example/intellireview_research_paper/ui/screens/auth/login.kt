package com.example.intellireview_research_paper.ui.screens

import UserViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    // Local UI state for email/password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Factory to provide UserViewModel
    class UserViewModelFactory(private val repo: UserRepositoryImpl) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    // Obtain ViewModel
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepository))

    // Observe login state
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage
    val user = viewModel.user
    val context = LocalContext.current

    // On successful login, navigate
    LaunchedEffect(user) {
        if (user != null) {
            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
    // Show error toast
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
        // Banner image
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
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

            // Login button
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