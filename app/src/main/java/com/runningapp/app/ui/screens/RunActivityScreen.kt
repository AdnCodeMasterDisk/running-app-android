package com.runningapp.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.runningapp.app.R
import com.runningapp.app.ui.components.RunActivityStatsComponent
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.theme.custom_color_yellow


@Composable
fun RunActivityScreen() {
//    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "2.53 km",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.small_map),
                contentDescription = "Activity map preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp, 40.dp, 40.dp, 40.dp))
            )

            /* TO DO: real map goes here */

//            Surface(modifier = Modifier
//                .height(330.dp)
//                .fillMaxWidth(),
//                color = MaterialTheme.colorScheme.surfaceVariant
//            ) {
//                Text("To do map")
//            }
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .align(Alignment.BottomStart)
                    .size(75.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = custom_color_red)
            ) {
                Icon(Icons.Filled.Stop, "Stop", modifier = Modifier.size(64.dp), tint = Color.White)
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(100.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = custom_color_yellow)
            ) {
                Icon(Icons.Filled.Pause, "Pause", modifier = Modifier.size(64.dp), tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        RunActivityStatsComponent()
    }
}


@Preview(showBackground = true)
@Composable
fun RunActivityScreenPreview() {
    RunActivityScreen()
}
