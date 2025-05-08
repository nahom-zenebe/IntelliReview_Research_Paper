package com.example.intellireview_research_paper

import Bookmark
import BottomNavBar
import LoginScreen
import UserProfileScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.intellireview_research_paper.ui.components.PostingScreen
import com.example.intellireview_research_paper.ui.navigation.Screen
import com.example.intellireview_research_paper.ui.screens.HomeScreen
import com.example.intellireview_research_paper.ui.theme.IntelliReview_Research_PaperTheme
import com.example.intellireview_research_paper.viewmodel.CreatePostViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntelliReview_Research_PaperTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screens = listOf(
        Screen.Home,
        Screen.Favourites,
        Screen.Grid,
        Screen.Messages,
        Screen.Profile
    )

    val selectedIndex = screens.indexOfFirst { it.route == currentRoute }
        .takeIf { it != -1 } ?: 0

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedIndex,
                onItemSelected = { index ->
                    navController.navigate(screens[index].route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Favourites.route) {
                Bookmark(onMenuClick = {})
            }
            composable(Screen.Grid.route) {
                LoginScreen()
            }
            composable(Screen.Messages.route) {
                // Provide the ViewModel here:
                val createPostViewModel: CreatePostViewModel = viewModel()
                PostingScreen(viewModel = createPostViewModel)
            }
            composable(Screen.Profile.route) {
                UserProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    IntelliReview_Research_PaperTheme {
        MainScreen()
    }
}
