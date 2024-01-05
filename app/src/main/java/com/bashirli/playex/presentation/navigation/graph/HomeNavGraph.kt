package com.bashirli.playex.presentation.navigation.graph

import android.util.Log
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
            Log.e("albumId", albumId.toString())
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

}


