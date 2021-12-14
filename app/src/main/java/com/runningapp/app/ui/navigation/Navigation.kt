package com.runningapp.app.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
    object RunMode : NavigationItem("runMode", Icons.Outlined.DirectionsRun, "runMode")
    object FinishedRunMode : NavigationItem("finishedRunMode", Icons.Outlined.DirectionsRun, "finishedRunMode")
    object SplashScreen : NavigationItem("splash",Icons.Outlined.Splitscreen,"Splash Screen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController) {
    val dataItems = (0..10).map { SimpleListDataItem("Username") }
    val dataItems2 = (0..10).map { SimpleListDataItem("30 km in October") }
    NavHost(navController, startDestination = NavigationItem.SplashScreen.route) {
        composable(NavigationItem.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavigationItem.Explore.route) {
            ExploreScreen(modifier = Modifier.fillMaxSize())
        }
        composable(NavigationItem.Challenges.route) {
            ChallengesScreen(dataItems2)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen()
        }
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.Register.route) {
            RegisterScreen(navController)
        }
        composable(NavigationItem.RunMode.route) {
            RunActivityScreen(navController)
        }
        composable(NavigationItem.FinishedRunMode.route) {
            FinishedActivityScreen()
        }
    }
}