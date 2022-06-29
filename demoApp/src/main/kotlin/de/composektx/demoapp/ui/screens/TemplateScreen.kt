package de.composektx.demoapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import de.composektx.demoapp.ui.navigation.NavigationDestination

@Composable
fun TemplateScreen(navController: NavHostController, title: String, navigationDestinationTitle: String, navigationDestination: NavigationDestination) = Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Text(text = title)
    Button(onClick = {
        navController.navigate(navigationDestination.route)
    } ) {
        Text(text = "Go to $navigationDestinationTitle")
    }
}