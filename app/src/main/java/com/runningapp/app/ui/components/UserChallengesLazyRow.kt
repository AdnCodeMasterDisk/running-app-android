package com.runningapp.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.viewmodel.ChallengesViewModel

@Composable
fun UserChallengesLazyRow(
    viewModel: ChallengesViewModel = hiltViewModel()
) {
    val userId = viewModel.userId.observeAsState()

    LaunchedEffect(key1 = true) {
        userId.value?.let { viewModel.getAllUserChallenges(it) }
    }

    val userChallengesState = viewModel.userChallengesState.value
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f)){
            Text(
                text = "Challenges",
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Row() {
            Icon(
                imageVector =  Icons.Outlined.EmojiEvents,
                contentDescription = "Challenges",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = (userChallengesState.userChallenges.count { it.isCompleted }).toString() + " completed",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    if (userChallengesState.isLoading) {
        LazyRow(modifier = Modifier.padding(horizontal = 4.dp)) {
            repeat(5) {
                item {
                    ShimmerAnimation("challenge")
                }
            }
        }
    }
    if (userChallengesState.userChallenges.isNotEmpty()) {
        LazyRow(modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth()) {
            items(userChallengesState.userChallenges) { data ->
                UserChallengeTile(userChallenge = data)
            }
        }
    }
    if (userChallengesState.error.isNotBlank()) {
        val errorMsg = if (userChallengesState.error == "HTTP 404 ") "You have no challenges"
        else userChallengesState.error
        Text(
            text = errorMsg,
            color = custom_color_red,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}