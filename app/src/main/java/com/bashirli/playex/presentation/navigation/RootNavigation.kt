package com.bashirli.playex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bashirli.playex.presentation.ui.screens.main.MainScreen
import com.bashirli.playex.presentation.ui.screens.splash.SplashScreen

@Composable
fun RootNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN
    ) {

        composable(route = Graph.MAIN) {
            MainScreen()
        }

        composable(route = Graph.SPLASH) {
            SplashScreen(onNavigate = {
                navHostController.navigate(it) {
                    popUpTo(Graph.SPLASH) {
                        inclusive = true
                    }
                }
            })
        }
    }
}

object Graph {
    const val ROOT = "root_navigation"
    const val SPLASH = "splash_graph"
    const val MAIN = "main_graph"
    const val HOME = "home_graph"
}
