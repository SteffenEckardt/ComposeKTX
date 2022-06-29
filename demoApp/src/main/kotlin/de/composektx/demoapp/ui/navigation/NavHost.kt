package de.composektx.demoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.composektx.demoapp.ui.navigation.NavigationDestination.*
import de.composektx.demoapp.ui.screens.DetailScreen
import de.composektx.demoapp.ui.screens.HomeScreen
import de.composektx.demoapp.ui.screens.ListScreen

@Composable
fun SetupNavHost(navController: NavHostController) = NavHost(navController = navController, startDestination = "home") {
    composable(HomeDestination.route) {
        HomeScreen(navController)
    }
    composable(ListDestination.route) {
        ListScreen(navController)
    }
    composable(DetailDestination.route, arguments = listOf(navArgument("id"){
        this.type = NavType.IntType
    })) {
        val elementId = it.arguments?.getInt("id")
        requireNotNull(elementId)
        DetailScreen(navController, elementId)
    }
}

