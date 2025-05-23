
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.data.mapper.CategoryRepository
import com.example.intellireview_research_paper.data.mapper.NotificationRepository
import com.example.intellireview_research_paper.data.repository.NotificationRepositoryImpl
import com.example.intellireview_research_paper.ui.components.NotificationDisplayCard
import com.example.intellireview_research_paper.ui.screens.category.CategoryViewModelFactory
import com.example.intellireview_research_paper.ui.theme.White
import com.example.intellireview_research_paper.ui.theme.deepBlue
import com.example.intellireview_research_paper.viewmodel.CategoryViewModel
import com.example.intellireview_research_paper.viewmodel.NotificationUiState
import com.example.intellireview_research_paper.viewmodel.NotificationViewModel


class NotificationViewModelFactory(
    private val repository: NotificationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



@Composable
fun NotificationScreen(
    navController: NavController,
    repository: NotificationRepository
) {
    val context = LocalContext.current
    val viewModel: NotificationViewModel = viewModel(
        factory = NotificationViewModelFactory(repository)
    )

    // Get user role from SharedPreferences
    val prefs = remember {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
    val role = prefs.getString("KEY_ROLE", "") ?: ""
    val isAdmin = role.equals("admin", ignoreCase = true)

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    // Fetch notifications when screen launches
    LaunchedEffect(Unit) {
        viewModel.fetchNotifications()
    }

    LaunchedEffect(viewModel.notificationCreated) {
        viewModel.notificationCreated.collect { notification ->
            Toast.makeText(
                context,
                "Notification '${notification.title}' created!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Only show creation form for admins
        if (isAdmin) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = deepBlue,
                        shape = RoundedCornerShape(16.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title Field
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Title",
                    fontWeight = FontWeight.Light,
                    fontSize = 23.sp,
                    color = White
                )
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE6E6FA),
                        unfocusedContainerColor = Color(0xFFE6E6FA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Message Field
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Message",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Light,
                    color = White
                )
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE6E6FA),
                        unfocusedContainerColor = Color(0xFFE6E6FA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    singleLine = false
                )

                // Create Button
                Button(
                    onClick = {
                        viewModel.createNotification(title, message)
                        title = ""
                        message = ""
                    },
                    modifier = Modifier
                        .width(190.dp)
                        .height(35.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Create",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Notification List (visible to all users)
        when (val state = viewModel.uiState.collectAsState().value) {
            is NotificationUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is NotificationUiState.Success -> {
                LazyColumn {
                    items(state.notifications) { notification ->
                        NotificationDisplayCard(
                            title = notification.title ?: "No title",
                            time = notification.createdAt ?: "Unknown time",
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
            is NotificationUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.fetchNotifications() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            NotificationUiState.Idle -> {
                // Initial state
            }

            else -> {}
        }
    }
}