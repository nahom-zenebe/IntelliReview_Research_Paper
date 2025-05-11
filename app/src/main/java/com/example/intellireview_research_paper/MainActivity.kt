package com.example.intellireview_research_paper


import BookmarkScreen
import BottomNavBar
import CreateAccountScreen
import NotificationScreen
import UserApiClient
import UserProfileScreen
import UserViewModel
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
import com.example.intellireview_research_paper.data.remote.CategoryApi
import com.example.intellireview_research_paper.data.remote.CategoryApiClient
import com.example.intellireview_research_paper.data.remote.NotificationApi
import com.example.intellireview_research_paper.data.remote.NotificationApiClient
import com.example.intellireview_research_paper.data.remote.UserApi
import com.example.intellireview_research_paper.data.repository.CategoryRepositoryImpl
import com.example.intellireview_research_paper.data.repository.NotificationRepositoryImpl
import com.example.intellireview_research_paper.ui.components.PostingScreen
import com.example.intellireview_research_paper.ui.navigation.Screen
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
object ApiProvider {
    val userApi: UserApi = UserApiClient.apiService
}

object ApiProvidercategory {
    val categoryApi: CategoryApi = CategoryApiClient.apiService
}
object ApiProviderNotification {
    val notificationApi: NotificationApi = NotificationApiClient.apiService
}


@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val userRepository = remember {
        UserRepositoryImpl(ApiProvider.userApi)
    }
    val userViewModel = UserViewModel(userRepository)
    val categoryRepository= remember {
        CategoryRepositoryImpl(ApiProvidercategory.categoryApi)
    }
    val notificationRepository= remember {
        NotificationRepositoryImpl(ApiProviderNotification.notificationApi)
    }
    val screens = listOf(
        Screen.Home,
        Screen.Favourites,
        Screen.Grid,
        Screen.Profile,
        Screen.CreateNotification,
        Screen.createCategory,
        Screen.Messages,



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
                HomeScreen(navController)
            }
            composable(Screen.Favourites.route) {

                BookmarkScreen(
                    onMenuClick = { /* Handle menu button click */ },
                    navController = navController,
                )

            }
//            composable(Screen.Grid.route) {
//               CreateAccountScreen(navController = navController,userRepository = userRepository)
//            }
            composable(Screen.Grid.route) {
              CategoryView(navController = navController,repository = categoryRepository)
            }

            composable(Screen.Messages.route) {
                // Provide the ViewModel here:
                val createPostViewModel: CreatePostViewModel = viewModel()
                PostingScreen(viewModel = createPostViewModel)
            }

            composable(Screen.Profile.route) {
                UserProfileScreen(viewModel = userViewModel)
//                                LoginScreen(
//                                    navController = navController,
//                                    userRepository = userRepository,
//                                    onBackClick = {}
//                                )
            }
            composable(Screen.createCategory.route) {

                CreateCategoryScreen(
                    navController = navController,
                    repository = categoryRepository
                )

            }
            composable(Screen.CreateNotification.route) {

                NotificationScreen(
                    navController = navController,
                    repository = notificationRepository
                )

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
