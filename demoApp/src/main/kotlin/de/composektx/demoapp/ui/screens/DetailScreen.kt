package de.composektx.demoapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import de.composektx.demoapp.ui.navigation.NavigationDestination
import de.composektx.demoapp.ui.navigation.NavigationDestination.HomeDestination

@Composable
@Destination("Details", true)
fun DetailScreen(navHostController: NavHostController, elementId: Int) = TemplateScreen(
    title = "Detail ($elementId)",
    navController = navHostController,
    navigationDestinationTitle = "Home",
    navigationDestination = HomeDestination
)

annotation class Destination(val name: String = "", val useNavigationDefaults: Boolean = false)

/**
 * @author Generated
 * @param id
 *
 */
fun NavHostController.navigateToDetails(id: Int) =
    navigate(NavigationDestination.DetailDestination.route.replace("{id}", id.toString()))
