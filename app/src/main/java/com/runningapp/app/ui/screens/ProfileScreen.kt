package com.runningapp.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.runningapp.app.ui.components.ChallengeTile
import com.runningapp.app.ui.components.MonthlyGoalComponent
import com.runningapp.app.ui.utils.SimpleListDataItem

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, simpleListDataItems: List<SimpleListDataItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MonthlyGoalComponent()
        Box(
            modifier = Modifier
                .padding(12.dp)
                .height(250.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
        ) {
            Text("To do chart")
        }
        Text(
            text = "Challenges",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(12.dp)
        )
        LazyRow(modifier = modifier) {
            items(simpleListDataItems) { data ->
                ChallengeTile(simpleListDataItem = data)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val dataItems = (0..10).map { SimpleListDataItem("30 km in October") }
    ProfileScreen(modifier = Modifier.fillMaxSize(), dataItems)
}