package com.kproject.kmemes.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.kproject.kmemes.ui.screens.ImageViewerScreen
import com.kproject.kmemes.ui.screens.MainScreen

@ExperimentalFoundationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.ImageViewerScreen.route + "/{imageUrl}",
            arguments = listOf(
                navArgument(name = "imageUrl") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            ImageViewerScreen(
                navController = navController,
                imageUrl = entry.arguments!!.getString("imageUrl", null)
            )
        }
    }
}