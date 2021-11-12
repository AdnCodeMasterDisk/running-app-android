package com.runningapp.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// Floating Action Button to open Run mode

@Composable
fun RunModeFAB() {
    FloatingActionButton(
        onClick = { /* do something */ },
        containerColor = MaterialTheme.colorScheme.tertiaryContainer) {
        Icon(Icons.Filled.PlayArrow, "Localized description")
    }
}