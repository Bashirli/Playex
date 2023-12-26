package com.bashirli.playex.presentation.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bashirli.playex.presentation.navigation.Graph
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

        composable(route = BottomScreen.ProfileScreen.route) {

        }


    }

}


