package com.runningapp.app.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.runningapp.app.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val authToken = viewModel.token.observeAsState()
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        if (authToken.value != null) {
            navController.popBackStack()
            navController.navigate("home")
        } else {
            navController.popBackStack()
            navController.navigate("login")
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
           Icons.Filled.DirectionsRun,
           contentDescription = "Run logo",
           modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Running app",
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 36.dp)
        )
    }
}