package com.runningapp.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.runningapp.app.ui.screens.*
import com.runningapp.app.ui.utils.SimpleListDataItem


sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Profile : NavigationItem("profile", Icons.Outlined.Person, "Profile")
    object Home : NavigationItem("home", Icons.Outlined.DirectionsRun, "Home")
    object Explore : NavigationItem("explore", Icons.Outlined.Explore, "Explore")
    object Challenges : NavigationItem("challenges", Icons.Outlined.EmojiEvents, "Challenges")
    object Settings : NavigationItem("settings", Icons.Outlined.Settings, "Settings")
    object Login : NavigationItem("login", Icons.Outlined.Login, "Login")
    object Register : NavigationItem("register", Icons.Outlined.AppRegistration, "Register")
}

@Composable
fun Navigation(navController: NavHostController) {
    val dataItems = (0..10).map { SimpleListDataItem("Username") }
    val dataItems2 = (0..10).map { SimpleListDataItem("30 km in October") }
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(modifier = Modifier.fillMaxSize(), dataItems2)
        }
        composable(NavigationItem.Explore.route) {
            ExploreScreen(modifier = Modifier.fillMaxSize(), dataItems)
        }
        composable(NavigationItem.Challenges.route) {
            ChallengesScreen()
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen()
        }
        composable(NavigationItem.Login.route) {
            LoginScreen()
        }
        composable(NavigationItem.Register.route) {
            RegisterScreen()
        }
    }
}