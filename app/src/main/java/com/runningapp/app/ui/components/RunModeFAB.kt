package com.runningapp.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// Floating Action Button to open Run mode

@Composable
fun RunModeFAB() {
    val openDialog = remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = { openDialog.value = true },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Filled.PlayArrow, "Localized description")
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            icon = {
                Icon(
                    Icons.Filled.DirectionsRun,
                    contentDescription = null
                ) },
            title = {
                Text(text = "Running activity")
            },
            text = {
                Text("Do you want to start a running activity?")
            },
            confirmButton = {
                FilledTonalButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}