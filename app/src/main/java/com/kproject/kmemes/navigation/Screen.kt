package com.kproject.kmemes.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ImageViewerScreen : Screen("image_viewer_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}