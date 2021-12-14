package com.runningapp.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.runningapp.app.domain.model.Challenge
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.theme.custom_color_yellow
import com.runningapp.app.ui.viewmodel.ChallengesViewModel

@Composable
fun ChallengeTile(
    challenge: Challenge,
    viewModel: ChallengesViewModel = hiltViewModel()
) {
    val userId = viewModel.userId.observeAsState()
    val isParticipating = remember { mutableStateOf(false) }
    val finished = remember { mutableStateOf(false) }
    val hasActiveChallenge = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        userId.value?.let { viewModel.getAllUserChallenges(it) }
    }
    val userChallengesState = viewModel.userChallengesState.value

    if (userChallengesState.userChallenges.any { !it.isCompleted }) {
        hasActiveChallenge.value = true
    }

    if (userChallengesState.userChallenges.any { it.challenge.id == challenge.id && it.isCompleted }) {
        finished.value = true
    }

    if (userChallengesState.userChallenges.any { it.challenge.id == challenge.id }) {
        isParticipating.value = true
    }

    val openDialog = remember { mutableStateOf(false)}

    val joinChallengeState = viewModel.joinChallengeState.value


    Box(modifier = Modifier
        .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 12.dp)
        .fillMaxSize()
    ) {
        Surface(
            color = if (isParticipating.value) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(120.dp, 160.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable(
                    enabled = true,
                    onClickLabel = "Join challenge",
                    onClick = {
                        openDialog.value = true
                    },
                ),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = challenge.name,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = challenge.participantsAmount.toString(),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = if (challenge.participantsAmount == 1) "participant" else "participants",
                    style = MaterialTheme.typography.labelSmall
                )
                LinearProgressIndicator(
                    progress = if (finished.value) 1f else 0.02f,
                    backgroundColor = Color.White,
                    color = custom_color_yellow,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(10.dp)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = if (finished.value) "Finished" else if (isParticipating.value && !finished.value) "In progress" else challenge.amountToComplete.toString() + " km",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        if (openDialog.value && !finished.value && !isParticipating.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = challenge.name)
                },
                text = {
                    if (joinChallengeState.isLoading) {
                        CircularProgressIndicator()
                    } else if (joinChallengeState.responseBody != null) {
                        Text(text = "Successfully joined challenge!")
                        openDialog.value = false
                        viewModel.clearState()
                        viewModel.getAllChallenges()
                    } else if (joinChallengeState.error.isNotBlank()) {
                        Text(
                            text = joinChallengeState.error,
                            color = custom_color_red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    } else {
                        Text(text = challenge.description)
                    }
                },
                confirmButton = {
                    if (!hasActiveChallenge.value) {
                        Button(
                            onClick = {
                                userId.value?.let { viewModel.joinChallenge(it, challenge.id) }
                            }
                        ) {
                            Text("Join challenge")
                        }
                    } else {
                        Button(
                            enabled = false,
                            onClick = {
                            }
                        ) {
                            Text("You have an active challenge")
                        }
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
