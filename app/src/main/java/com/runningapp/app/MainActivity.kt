package com.runningapp.app

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.runningapp.app.ui.components.RunModeFAB
import com.runningapp.app.ui.navigation.*
import com.runningapp.app.ui.theme.RunningAppTheme

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

    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,

        topBar = {
            if(currentRoute(navController) != "runMode") {
               TopBar(scope, scaffoldState)
            }
        },
        floatingActionButton = {
            if(currentRoute(navController)  != "runMode" || currentRoute(navController)  != "home") {
                RunModeFAB()
            }
        },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        },
        bottomBar = { BottomNavigationBar(navController) },
        content = { Navigation(navController)},
        scaffoldState = scaffoldState
    )
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    return navController.currentBackStackEntry?.destination?.route
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