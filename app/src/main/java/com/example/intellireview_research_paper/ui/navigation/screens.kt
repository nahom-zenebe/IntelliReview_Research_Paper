package com.example.intellireview_research_paper.ui.navigation

sealed class Screen(val route: String) {
    object Welcome             : Screen("welcome")
    object Login               : Screen("login")
    object CreateAccountScreen : Screen("signup")
    object Home                : Screen("home")
    object Favourites          : Screen("favourites")
    object Grid                : Screen("grid")
    object Messages            : Screen("messages")
    object Profile             : Screen("profile")
    object createCategory      : Screen("createCategory")
    object CreateNotification  : Screen("createnotification")
    object AdminDashboard      : Screen("admindashboard")
    object NotificationScreen  : Screen("noticationscreen")
    object PostingScreen       : Screen("posting")
    object commentScreen       :Screen("commentscreen")
}
