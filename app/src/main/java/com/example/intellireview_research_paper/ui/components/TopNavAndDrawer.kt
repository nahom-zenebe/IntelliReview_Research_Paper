package com.example.intellireview_research_paper.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.intellireview_research_paper.R
import com.example.intellireview_research_paper.ui.navigation.Screen
import com.example.intellireview_research_paper.ui.screens.LoginScreen

@Composable
fun HomeTopBar(
    onMenuClick: () -> Unit,
    inputname: String,
    onNotificationClick: () -> Unit // Add click handler parameter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF36454F),
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = inputname,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF36454F)
            )
        }

        // Change to IconButton and add click handler
        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Color(0xFF36454F)
            )
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    onLogout: () -> Unit
) {

    val context = LocalContext.current

    // Read saved user info from SharedPreferences each time
    val prefs = remember {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
    val name  = prefs.getString("KEY_NAME", "")  ?: ""
    val email = prefs.getString("KEY_EMAIL", "") ?: ""
    val role  = prefs.getString("KEY_ROLE", "")  ?: ""

    Log.d("UserProfileScreen", "Name: $name, Email: $email, Role: $role")


    val items = listOf(
        "Home" to Screen.Home.route,
        "Profile" to Screen.Profile.route,
        "Bookmark" to Screen.Favourites.route,
        "Create Category" to Screen.createCategory.route,
        "View Category" to Screen.Grid.route
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
    ) {
        // Header with profile, name, and email
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5D5CBB))
                .height(180.dp)
                .padding(top = 24.dp, start = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.welcome_screen_container),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = name.ifBlank { "Guest User" },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
            Text(
                text = email.ifBlank { "no email" },
                fontSize = 14.sp,
                color = Color.White
            )
        }

        // Drawer menu body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8E8ED))
                .padding(start = 16.dp, top = 24.dp, bottom = 24.dp)
        ) {
            items.forEach { (label, route) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(vertical = 16.dp)
                ) {
                    val icon = when (label) {
                        "Home" -> Icons.Outlined.Home
                        "Profile" -> Icons.Outlined.Person
                        "Bookmark" -> Icons.Outlined.FavoriteBorder
                        "Create Category", "View Category" -> Icons.Outlined.GridView
                        else -> Icons.Outlined.Home
                    }

                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = Color(0xFF36454F),
                        modifier = Modifier.size(23.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = label,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF36454F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(250.dp))

            HorizontalDivider(modifier = Modifier.padding(end = 16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // 1. First execute the logout logic
                        onLogout()

                        // 2. Then navigate to login screen with cleared back stack
                        navController.navigate(Screen.Login.route) {
                            // Clear the entire back stack
                            popUpTo(0)

                            // Optional: Prevent multiple login screens
                            launchSingleTop = true
                        }
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xFF36454F),
                    modifier = Modifier.size(23.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Logout",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF36454F)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerContentPreview() {
    MaterialTheme {
        // Create a mock NavController for preview
        val navController = rememberNavController()

        // State to show if logout was clicked
        var showLogoutMessage by remember { mutableStateOf(false) }

        // Mock SharedPreferences data
        val context = LocalContext.current
        val mockPrefs = remember {
            context.getSharedPreferences("preview_prefs", Context.MODE_PRIVATE).apply {
                edit()
                    .putString("KEY_NAME", "Jane Smith")
                    .putString("KEY_EMAIL", "jane@example.com")
                    .putString("KEY_ROLE", "Researcher")
                    .apply()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            DrawerContent(
                navController = navController,
                onLogout = {
                    // This will be called when logout is clicked
                    showLogoutMessage = true
                    println("Logout clicked in preview!")
                }
            )

            if (showLogoutMessage) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f))
                ) {
                    Text(
                        text = "Logout Clicked!",
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
