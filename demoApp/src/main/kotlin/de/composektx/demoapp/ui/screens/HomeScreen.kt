package de.composektx.demoapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import de.composektx.demoapp.ui.navigation.NavigationDestination.*

@Composable
fun HomeScreen(navHostController: NavHostController) = TemplateScreen(
    title = "Home",
    navController = navHostController,
    navigationDestinationTitle = "List",
    navigationDestination = ListDestination
)
