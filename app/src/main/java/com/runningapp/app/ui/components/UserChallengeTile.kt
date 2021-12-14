package com.runningapp.app.ui.components

import androidx.compose.foundation.clickable
import com.runningapp.app.domain.model.UserChallenges
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.theme.custom_color_yellow
import com.runningapp.app.ui.viewmodel.ChallengesViewModel

@Composable
fun UserChallengeTile(
    userChallenge: UserChallenges,
    viewModel: ChallengesViewModel = hiltViewModel()
) {

    val openDialog = remember { mutableStateOf(false) }
    val userId = viewModel.userId.observeAsState()
    val leaveChallengeState = viewModel.leaveChallengeState.value

    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxSize()
    ) {
        Surface(
            color = if (userChallenge.isCompleted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(120.dp, 160.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable(
                    enabled = true,
                    onClickLabel = "Delete challenge",
                    onClick = { openDialog.value = true }
                ),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = userChallenge.challenge.name,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = userChallenge.challenge.participantsAmount.toString(),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = if (userChallenge.challenge.participantsAmount == 1) "participant" else "participants",
                    style = MaterialTheme.typography.labelSmall
                )
                LinearProgressIndicator(
                    progress = if (userChallenge.isCompleted) 1f else ((userChallenge.currentAmount / 1000) / userChallenge.challenge.amountToComplete).toFloat(),
                    backgroundColor = Color.White,
                    color = custom_color_yellow,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(10.dp)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = if (userChallenge.isCompleted) "Finished" else (((userChallenge.currentAmount / 1000) / userChallenge.challenge.amountToComplete) * 100).toInt()
                        .toString() + "%",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = userChallenge.challenge.name)
                },
                text = {
                    if (leaveChallengeState.isLoading) {
                        CircularProgressIndicator()
                    } else if (leaveChallengeState.responseBody != null) {
                        Text(text = "Successfully deleted challenge!")
                        openDialog.value = false
                        viewModel.clearState()
                        userId.value?.let { viewModel.getAllUserChallenges(it) }
                    } else if (leaveChallengeState.error.isNotBlank()) {
                        Text(
                            text = leaveChallengeState.error,
                            color = custom_color_red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    } else {
                        Text(text = userChallenge.challenge.description)
                    }
                },
                confirmButton = {
                    if (!userChallenge.isCompleted) {
                        Button(
                            onClick = {
                                userId.value?.let {
                                    viewModel.leaveChallenge(
                                        it,
                                        userChallenge.challenge.id
                                    )
                                }
                            }
                        ) {
                            Text("Leave challenge")
                        }
                    } else {
                        Button(
                            enabled = false,
                            onClick = {}
                        ) {
                            Text("Challenge is finished")
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

@Preview
@Composable
fun ChallengeTilePreview() {
    //  ChallengeTile(SimpleListDataItem("30 km in October"))
}