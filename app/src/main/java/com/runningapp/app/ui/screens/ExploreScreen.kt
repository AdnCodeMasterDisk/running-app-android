package com.runningapp.app.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.runningapp.app.ui.components.ActivityCard
import com.runningapp.app.ui.components.RecommendedChallengeCard
import com.runningapp.app.ui.components.ShimmerAnimation
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.viewmodel.RunListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: RunListViewModel = hiltViewModel()
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column {
            RecommendedChallengeCard(name = "Complete a 10 km run")
            val state = viewModel.state.value
            if(state.isLoading) {
                LazyColumn {
                    repeat(5) {
                        item {
                            ShimmerAnimation("activity")
                        }
                    }
                }
            }
            if (state.runs.isNotEmpty()) {
                LazyColumn(modifier = modifier) {
                    items(state.runs) { run ->
                        ActivityCard(runActivity = run)
                    }
                }
            }
            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = custom_color_red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
   // ExploreScreen(modifier = Modifier.fillMaxSize())
}