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
import de.composektx.demoapp.ui.navigation.NavigationDestination.*
import de.composektx.demoapp.ui.navigation.toDestination

@Composable
fun ListScreen(navHostController: NavHostController) = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.SpaceAround,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(text = "List")
    Button(onClick = { navHostController.navigateToDetails(1) }) {
        Text(text = "Detail-1")
    }
    Button(onClick = { navHostController.navigateToDetails(2) }) {
        Text(text = "Detail-2")
    }
    Button(onClick = { navHostController.navigateToDetails(3) }) {
        Text(text = "Detail-3")
    }
}