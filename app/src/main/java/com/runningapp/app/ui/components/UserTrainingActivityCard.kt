package com.runningapp.app.ui.components

import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.LocalFireDepartment
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.runningapp.app.data.remote.dto.MapImage
import com.runningapp.app.data.remote.dto.User
import com.runningapp.app.domain.model.RunActivity
import com.runningapp.app.ui.theme.custom_color_blue
import com.runningapp.app.ui.viewmodel.RunActivityViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserTrainingActivityCard(
    runActivity: RunActivity,
    viewModel: RunActivityViewModel = hiltViewModel()
) {
    val mapExpanded = remember { mutableStateOf(false) }
    val isShared = remember { mutableStateOf(false) }
    val mapSize by animateDpAsState(
        if (mapExpanded.value) 300.dp else 100.dp
    )

    if (runActivity.isPosted) isShared.value = true

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column {
            Row(
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp, start = 24.dp, end = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val parsedDate = LocalDateTime.parse(runActivity.date, DateTimeFormatter.ISO_DATE_TIME)
                    val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm"))
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                TextButton(
                    onClick = {
                        isShared.value = !isShared.value
                        viewModel.shareRun(runActivity.id)
                    }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.labelLarge,
                        text = if (isShared.value) {
                            "Unshare"
                        } else {
                            "Share"
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 0.dp, end = 24.dp, start = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Tour,
                            contentDescription = "Distance"
                        )
                        val distanceInKm = ((runActivity.distance / 1000f).toDouble())
                        val distanceInKmRounded =
                            BigDecimal(distanceInKm).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                        Text(
                            text = "$distanceInKmRounded km",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Timer,
                            contentDescription = "Total time"
                        )
                        Text(
                            text = runActivity.totalTime,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DirectionsRun,
                            contentDescription = "Avg Pace"
                        )
                        Text(
                            text = runActivity.pace,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
//            Row(
//                modifier = Modifier
//                    .padding(top = 12.dp, end = 24.dp, start = 24.dp)
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.LocalFireDepartment,
//                        contentDescription = "Calories"
//                    )
//                    Text(
//                        text = runActivity.calories.toString() + "kcal",
//                        modifier = Modifier.padding(start = 4.dp)
//                    )
//                }
//            }
                val imageBytes = Base64.decode(runActivity.mapImage.data, 0)
                val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                ) {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "Activity map preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
                            .weight(1f)
                            .size(mapSize)
                            .clickable(
                                enabled = true,
                                onClickLabel = "Expand map",
                                onClick = {
                                    mapExpanded.value = !mapExpanded.value
                                }
                            )
                    )
                }
            }
        }

}
