package com.runningapp.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runningapp.app.ui.components.ActivityCard
import com.runningapp.app.ui.components.ChallengeTile
import com.runningapp.app.ui.components.MonthlyGoalComponent
import com.runningapp.app.ui.utils.SimpleListDataItem

@Composable
fun HomeScreen(modifier: Modifier = Modifier, simpleListDataItems: List<SimpleListDataItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MonthlyGoalComponent()
        Text(
            text = "Home View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "Challenges",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 12.dp)
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
fun HomeScreenPreview() {
    val dataItems = (0..10).map { SimpleListDataItem("30 km in October") }
    HomeScreen(modifier = Modifier.fillMaxSize(), dataItems)
}
