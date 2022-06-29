package de.composektx.demoapp.ui.navigation

open class NavigationDestination(val route: String) {
    object HomeDestination : NavigationDestination("home")
    object ListDestination : NavigationDestination("list")
    object DetailDestination : NavigationDestination("detail/{id}")
}

fun String.toDestination() = NavigationDestination(route = this)