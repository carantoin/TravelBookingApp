package com.example.travelbooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelbooking.ui.navigation.Screen
import com.example.travelbooking.ui.screens.DetailScreen
import com.example.travelbooking.ui.screens.FavoritesScreen
import com.example.travelbooking.ui.screens.HomeScreen
import com.example.travelbooking.ui.screens.OnboardingScreen
import com.example.travelbooking.ui.theme.TravelBookingTheme
import com.example.travelbooking.ui.viewmodel.TravelViewModel
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .logger(DebugLogger())
                .crossfade(true)
                .build()
        )
        enableEdgeToEdge()
        setContent {
            TravelBookingTheme {
                TravelApp()
            }
        }
    }
}

@Composable
fun TravelApp() {
    val navController = rememberNavController()
    val travelViewModel: TravelViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        // 1. Onboarding Screen
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onExploreClicked = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Main Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = travelViewModel,
                onDestinationClicked = { destinationId ->
                    navController.navigate(Screen.Detail.createRoute(destinationId))
                },
                onFavoritesClicked = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }

        // 3. Saved / Favorites Screen
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = travelViewModel,
                onDestinationClicked = { destinationId ->
                    navController.navigate(Screen.Detail.createRoute(destinationId))
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Destination Detail Screen
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("destinationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val destinationId = backStackEntry.arguments?.getString("destinationId") ?: ""
            DetailScreen(
                destinationId = destinationId,
                viewModel = travelViewModel,
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}
