package com.runningapp.app.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.runningapp.app.ui.components.RunModeFAB
import com.runningapp.app.ui.navigation.*
import com.runningapp.app.ui.theme.RunningAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunningAppTheme {
                MyApp()
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        drawerGesturesEnabled = false,

        topBar = {
            if (navController.currentRoute() != "runMode" && navController.currentRoute() != "splash") {
                val topBarText = when (navController.currentRoute()) {
                    "home" -> "Hello Adam!"
                    "profile" -> "Your profile"
                    "explore" -> "Explore the community"
                    "challenges" -> "Challenges"
                    "settings" -> "Settings"
                    else -> "Running App"
                }
                TopBar(scope, scaffoldState, topBarText)
            }
        },
        floatingActionButton = {
            if (navController.currentRoute() != "runMode" && navController.currentRoute() != "home" && navController.currentRoute() != "splash") {
                RunModeFAB()
            }
        },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        },
        bottomBar = {
            if (navController.currentRoute() != "runMode" && navController.currentRoute() != "splash") {
                BottomNavigationBar(navController)
            }
        },
        scaffoldState = scaffoldState
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                PaddingValues(0.dp, 0.dp, 0.dp, innerPadding.calculateBottomPadding())
            )
        ) {
            Navigation(navController)
        }
    }
}

@Composable
fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

// Preview for App
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 420,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 420)
@Composable
fun DefaultPreview() {
    RunningAppTheme {
        MyApp()
    }
}