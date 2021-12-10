package com.runningapp.app.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Tour
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.runningapp.app.R
import com.runningapp.app.domain.model.Run
import com.runningapp.app.ui.screens.ExploreScreen
import com.runningapp.app.ui.utils.SimpleListDataItem


@Composable
fun ActivityCard(
    run: Run
) {
    val expanded = remember { mutableStateOf(false) }
    val liked = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 18.dp else 0.dp
    )
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(20.dp))) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_picture),
                        contentDescription = "Contact profile picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Row {
                            Text(
                                text = run.date,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                        Row {
                            Text(
                                text = "TO DO",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = " shared activity"
                            )
                        }
                    }
                }
                IconButton(
                    onClick = { liked.value = !liked.value }
                ) {
                    Icon(
                        imageVector = if (liked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (liked.value) {
                            "Like"
                        } else {
                            "Dislike"
                        },
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                Text(text = if (liked.value) "1" else "0")
            }
            Row(
                modifier = Modifier.padding(bottom = 0.dp, end = 24.dp, start = 24.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Tour,
                        contentDescription = "Distance"
                    )
                    Text(
                        text = run.distance.toString(),
//                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Timer,
                        contentDescription = "Total time"
                    )
                    Text(
                        text = run.totalTime,
//                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.DirectionsRun,
                        contentDescription = "Avg Pace"
                    )
                    Text(
                        text = run.pace,
//                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

            }
            if (expanded.value) {
                Row(modifier = Modifier.padding(end = 24.dp, start = 24.dp, bottom = 4.dp, top = extraPadding).fillMaxWidth(),) {
                    Image(
                        painter = painterResource(R.drawable.small_map),
                        contentDescription = "Activity map preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .weight(1f)
                            .size(100.dp)
                    )
                }
            }
            IconButton(
                onClick = { expanded.value = !expanded.value },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded.value) {
                        "Show less"
                    } else {
                        "Show more"
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityCardPreview() {
   // ActivityCard()
}