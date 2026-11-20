package com.example.travelbooking.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Detail : Screen("detail/{destinationId}") {
        fun createRoute(destinationId: String) = "detail/$destinationId"
    }
}
