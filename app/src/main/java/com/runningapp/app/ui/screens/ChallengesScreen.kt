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
fun ChallengesScreen(simpleListDataItems: List<SimpleListDataItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = "Daily",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(simpleListDataItems) { data ->
                ChallengeTile(simpleListDataItem = data)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Weekly",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(simpleListDataItems) { data ->
                ChallengeTile(simpleListDataItem = data)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Monthly",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(simpleListDataItems) { data ->
                ChallengeTile(simpleListDataItem = data)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChallengesScreenPreview() {
    val dataItems = (0..10).map { SimpleListDataItem("30 km in October") }
    ChallengesScreen(dataItems)
}