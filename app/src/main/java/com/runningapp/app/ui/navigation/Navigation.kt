package com.runningapp.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.runningapp.app.ui.screens.ExploreScreen
import com.runningapp.app.ui.screens.HomeScreen
import com.runningapp.app.ui.screens.ProfileScreen
import com.runningapp.app.ui.utils.SimpleListDataItem


sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Profile : NavigationItem("profile", Icons.Outlined.Person, "Profile")
    object Home : NavigationItem("home", Icons.Outlined.DirectionsRun, "Home")
    object Explore : NavigationItem("explore", Icons.Outlined.Explore, "Explore")
}

@Composable
fun Navigation(navController: NavHostController) {
    val dataItems = (0..100).map { SimpleListDataItem("Username") }
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavigationItem.Explore.route) {
            ExploreScreen(modifier = Modifier.fillMaxSize(), dataItems)
        }
    }
}