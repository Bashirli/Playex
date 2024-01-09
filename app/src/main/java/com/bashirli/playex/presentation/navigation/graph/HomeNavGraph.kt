package com.bashirli.playex.presentation.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bashirli.playex.presentation.navigation.Graph
import com.bashirli.playex.presentation.ui.screens.albumDetails.AlbumDetailsScreen
import com.bashirli.playex.presentation.ui.screens.home.HomeScreen
import com.bashirli.playex.presentation.ui.screens.main.BottomScreen
import com.bashirli.playex.presentation.ui.screens.player.PlayerScreen


@Composable
fun HomeNavGraph(navHostController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navHostController,
        route = Graph.HOME,
        startDestination = BottomScreen.HomeScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable(route = BottomScreen.HomeScreen.route) {
            HomeScreen(onNavigate = {
                navHostController.navigate(it)
            })
        }

        composable(
            route = HomeScreenObject.AlbumDetails.route + "?{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.LongType })
        ) {
            val albumId = it.arguments?.getLong("albumId") ?: 0
            AlbumDetailsScreen(
                albumId = albumId,
                onBack = {
                    navHostController.popBackStack()
                },
                onNavigate = {
                    navHostController.navigate(it)
                }
            )
        }

        composable(
            route = HomeScreenObject.PlayerScreen.route + "?{audioId}",
            arguments = listOf(navArgument("audioId") { type = NavType.LongType })
        ) {
            val audioId = it.arguments?.getLong("audioId") ?: 0
            PlayerScreen(audioId = audioId, onBack = { navHostController.popBackStack() })
        }

        composable(route = BottomScreen.ProfileScreen.route) {

        }

        composable(route = BottomScreen.SearchScreen.route) {

        }


    }

}

sealed class HomeScreenObject(
    val route: String,
) {

    data object AlbumDetails : HomeScreenObject("album_details")

    data object PlayerScreen : HomeScreenObject("player_screen")

}


