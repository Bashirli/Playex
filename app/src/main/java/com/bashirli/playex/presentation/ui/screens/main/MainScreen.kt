package com.bashirli.playex.presentation.ui.screens.main

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bashirli.playex.R
import com.bashirli.playex.presentation.navigation.graph.HomeNavGraph
import com.bashirli.playex.presentation.ui.theme.Pink29
import com.bashirli.playex.presentation.ui.theme.Pink63
import com.bashirli.playex.presentation.ui.theme.fontFamily

@Composable
fun MainScreen(navHostController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomBar(navHostController = navHostController) }
    ) { innerPadding ->
        HomeNavGraph(navHostController = navHostController, innerPadding = innerPadding)
    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val screens = listOf(
        BottomScreen.HomeScreen,
        BottomScreen.ProfileScreen
    )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            containerColor = Pink63
        ) {
            screens.forEach { item ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true,
                    onClick = {
                        navHostController.navigate(item.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.name),
                            tint = Color.White
                        )
                    },
                    label = {
                        Text(
                            stringResource(item.name),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Pink29
                    )
                )
            }
        }
    }
}

sealed class BottomScreen(
    val route: String,
    @StringRes val name: Int,
    val icon: ImageVector,
    // @DrawableRes val icon : Int
) {

    data object HomeScreen :
        BottomScreen(route = "home", name = R.string.home_screen, icon = Icons.Default.Home)

    data object ProfileScreen : BottomScreen(
        route = "profile",
        name = R.string.profile_screen,
        icon = Icons.Default.AccountCircle
    )

}
