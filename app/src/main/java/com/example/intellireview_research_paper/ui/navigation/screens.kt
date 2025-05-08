package com.example.intellireview_research_paper.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favourites : Screen("favourites")
    object Grid : Screen("grid")
    object Messages : Screen("messages")
    object Profile : Screen("profile")
}
