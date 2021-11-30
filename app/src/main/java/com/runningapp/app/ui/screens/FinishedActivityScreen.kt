package com.runningapp.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.runningapp.app.ui.theme.custom_color_blue


@Composable
fun FinishedActivityScreen() {
//    val scrollState = rememberScrollState()
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
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
                .height(282.dp)
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
                onClick = { openDialog.value = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(75.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = custom_color_blue)
            ) {
                Icon(
                    Icons.Filled.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        RunActivityStatsComponent()

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    openDialog.value = false
                },
                icon = { Icon(Icons.Filled.Share, contentDescription = null) },
                title = {
                    Text(text = "Share")
                },
                text = {
                    Text("Do you want to share this activity?")
                },
                confirmButton = {
                    FilledTonalButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Share")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FinishedActivityScreenPreview() {
    FinishedActivityScreen()
}
