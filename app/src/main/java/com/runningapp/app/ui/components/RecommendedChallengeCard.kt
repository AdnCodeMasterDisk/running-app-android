package com.runningapp.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecommendedChallengeCard(name: String) {
    val hidden = remember { mutableStateOf(false) }
    if (!hidden.value) {
        Surface(
            onClick = { /* TO DO */ },
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Row(modifier = Modifier.padding(top = 8.dp, end = 8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { hidden.value = !hidden.value },
                    modifier = Modifier.then(Modifier.size(24.dp))) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close recommended challenge card"
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Recommended challenge",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "$name ",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "12 512",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "participants",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
//                IconButton(
//                    onClick = {/* TO DO */ },
//                    modifier = Modifier.then(Modifier.size(24.dp))){
//                    Icon(
//                        imageVector = Icons.Filled.ArrowForward,
//                        contentDescription = "Go to recommended challenge",
//                    )
//                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendedChallengeCardPreview() {
    RecommendedChallengeCard("Complete a 10 km run")
}