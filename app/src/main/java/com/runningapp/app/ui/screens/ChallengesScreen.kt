package com.runningapp.app.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.runningapp.app.ui.components.ChallengeTile
import com.runningapp.app.ui.components.MonthlyGoalComponent
import com.runningapp.app.ui.components.ShimmerAnimation
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.utils.SimpleListDataItem
import com.runningapp.app.ui.viewmodel.ChallengesViewModel
import com.runningapp.app.ui.viewmodel.RunListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengesScreen(
    viewModel: ChallengesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        val state = viewModel.state.value

        if (state.challenges.isNotEmpty()) {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(110.dp)
            ) {
                items(state.challenges) {
                    ChallengeTile(challenge = it)
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
        if(state.isLoading) {
            LazyColumn {
                repeat(5) {
                    item {
                        ShimmerAnimation("challenge")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChallengesScreenPreview() {
    ChallengesScreen()
}