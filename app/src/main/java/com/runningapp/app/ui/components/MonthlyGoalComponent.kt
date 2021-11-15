package com.runningapp.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MonthlyGoalComponent() {
    Surface(modifier = Modifier.padding(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Monthly Goal",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "23.52 km / 32.00 km",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Column {
                CircularProgressBar(progress = 0.6f, number = 100)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MonthlyGoalComponentPreview() {
    MonthlyGoalComponent()
}