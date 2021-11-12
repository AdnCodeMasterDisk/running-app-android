package com.runningapp.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.runningapp.app.ui.utils.SimpleListDataItem
import com.runningapp.app.ui.components.ActivityCard
import com.runningapp.app.ui.components.RecommendedChallengeCard

@Composable
fun ExploreScreen(modifier: Modifier = Modifier, simpleListDataItems: List<SimpleListDataItem>) {
    Column {
        RecommendedChallengeCard(name = "Complete a 10 km run")
        LazyColumn(modifier = modifier) {
            items(simpleListDataItems) { data ->
                ActivityCard(simpleListDataItem = data)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    val dataItems = (0..10).map { SimpleListDataItem("Username") }
    ExploreScreen(modifier = Modifier.fillMaxSize(), dataItems)
}