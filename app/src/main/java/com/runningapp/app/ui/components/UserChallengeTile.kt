package com.runningapp.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import com.runningapp.app.domain.model.UserChallenges
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.theme.custom_color_yellow

@Composable
fun UserChallengeTile(userChallenge: UserChallenges) {

    val expand = remember { mutableStateOf(false)}

    Box(modifier = Modifier
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
                    onClick = { expand.value = !expand.value }
                ),
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress= { },
//                        onLongPress = {
//                            expand.value = !expand.value
//                        },
//                        onTap = { expand.value = false }
//                    )
//                },
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
                // amount to complete 100km
                // current amount
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
                    text = if (userChallenge.isCompleted) "Finished" else (((userChallenge.currentAmount / 1000) / userChallenge.challenge.amountToComplete) * 100).toInt().toString() + "%",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        if (expand.value) {
            Button(
                onClick = { /* TODO remove impl */ },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(75.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = custom_color_red)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp),
                )
            }
        }
    }


}

@Preview
@Composable
fun ChallengeTilePreview() {
    //  ChallengeTile(SimpleListDataItem("30 km in October"))
}