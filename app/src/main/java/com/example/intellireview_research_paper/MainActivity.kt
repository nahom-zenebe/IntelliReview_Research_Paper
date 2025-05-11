package com.example.intellireview_research_paper

import AdminDashboard
import BottomNavBar
import CreateAccountScreen
import NotificationScreen
import UserProfileScreen
import UserViewModel
import WelcomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.intellireview_research_paper.data.mapper.UserRepositoryImpl
import com.example.intellireview_research_paper.data.remote.CategoryApiClient
import com.example.intellireview_research_paper.data.remote.NotificationApiClient

import com.example.intellireview_research_paper.data.repository.CategoryRepositoryImpl
import com.example.intellireview_research_paper.data.repository.NotificationRepositoryImpl

import com.example.intellireview_research_paper.ui.navigation.Screen
import com.example.intellireview_research_paper.ui.screens.BookmarkScreen
import com.example.intellireview_research_paper.ui.screens.CategoryView

import com.example.intellireview_research_paper.ui.screens.HomeScreen
import com.example.intellireview_research_paper.ui.screens.LoginScreen

import com.example.intellireview_research_paper.ui.screens.category.CreateCategoryScreen
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

// provide your retrofit clients
object ApiProvider {
    val userApi         = UserApiClient.apiService
    val categoryApi     = CategoryApiClient.apiService
    val notificationApi = NotificationApiClient.apiService
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStack     by navController.currentBackStackEntryAsState()
    val currentRoute  = backStack?.destination?.route

    // --- declare repos & VMs here ---
    val userRepo         = remember { UserRepositoryImpl(ApiProvider.userApi) }
    val userViewModel    = UserViewModel(userRepo)
    val categoryRepo     = remember { CategoryRepositoryImpl(ApiProvider.categoryApi) }
    val notificationRepo = remember { NotificationRepositoryImpl(ApiProvider.notificationApi) }

    // bottom‐tab list
    val tabs = listOf(
        Screen.Home,
        Screen.Favourites,
        Screen.Grid,
        Screen.Profile,
        Screen.Messages,
        Screen.createCategory,
        Screen.CreateNotification
    )
    val selectedTab = tabs.indexOfFirst { it.route == currentRoute }.takeIf { it != -1 } ?: 0

    Scaffold(
        bottomBar = {
            // hide on Welcome / Login / Sign‐up
            if (currentRoute !in listOf(
                    Screen.Welcome.route,
                    Screen.Login.route,
                    Screen.CreateAccountScreen.route
                )
            ) {
                BottomNavBar(
                    selectedItem  = selectedTab,
                    onItemSelected = { index ->
                        navController.navigate(tabs[index].route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navController = navController
                )
            }
        }
    ) { padding ->
        NavHost(
            navController     = navController,
            startDestination  = Screen.Welcome.route,
            modifier          = Modifier.padding(padding)
        ) {
            // 1) Welcome screen
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onLoginClick   = { navController.navigate(Screen.Login.route) },
                    onSignUpClick  = { navController.navigate(Screen.CreateAccountScreen.route) }
                )
            }

            // 2) Login
            composable(Screen.Login.route) {
                LoginScreen(
                    navController  = navController,
                    userRepository = userRepo,
                    onBackClick    = { navController.popBackStack() }
                )
            }

            // 3) Sign‐up
            composable(Screen.CreateAccountScreen.route) {
                CreateAccountScreen(
                    navController  = navController,
                    userRepository = userRepo
                )
            }

            // 4) Main tabs
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Favourites.route) {
                BookmarkScreen(
                    onLogout = { /*...*/ },
                    navController = navController
                )
            }
            composable(Screen.Grid.route) {
                CategoryView(navController, repository = categoryRepo)
            }
            composable(Screen.Profile.route) {
                UserProfileScreen(viewModel = userViewModel)
            }
            composable(Screen.Messages.route) {
                val postVm: CreatePostViewModel = viewModel()
                AdminDashboard(onMenuClick = { /*...*/ })
            }
            composable(Screen.createCategory.route) {
                CreateCategoryScreen(navController, repository = categoryRepo)
            }
            composable(Screen.CreateNotification.route) {
                NotificationScreen(navController, repository = notificationRepo)
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
