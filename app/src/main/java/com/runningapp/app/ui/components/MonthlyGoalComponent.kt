package com.runningapp.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.viewmodel.MonthlyGoalViewModel
import com.runningapp.app.ui.viewmodel.UserRunListViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

@Composable
fun MonthlyGoalComponent(
    viewModel: MonthlyGoalViewModel = hiltViewModel()
) {
    val openDialog = remember { mutableStateOf(false) }
    val sliderPosition = remember { mutableStateOf(0f) }

    val state = viewModel.state.value
    val monthlyGoal = viewModel.monthlyGoalPrefs.observeAsState()
    val userId = viewModel.userId.observeAsState()

    LaunchedEffect(key1 = true) {
        userId.value?.let { viewModel.getUserMonthlyDistance(it) }
        sliderPosition.value = monthlyGoal.value?.toFloat() ?: 30f
        viewModel.getMonthlyGoal()
    }

    Surface(modifier = Modifier.padding(12.dp)) {
        Column {
            if(state.isLoading) {
                ShimmerAnimation("monthlyGoal")
            }
            if (state.monthlyDistance != null) {
                val distanceInKm = ((state.monthlyDistance / 1000f).toDouble())
                val distanceInKmRounded =
                    BigDecimal(distanceInKm).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp, horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Monthly Goal",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = distanceInKmRounded.toString() + " km / " + (sliderPosition.value).roundToInt() + " km",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Column {
                        val progress = distanceInKmRounded / (sliderPosition.value).roundToInt()
                        CircularProgressBar(progress = progress.toFloat(), number = 100)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { openDialog.value = true }) {
                        Text(
                            text = "Change your goal",
                            style = MaterialTheme.typography.labelMedium
                        )
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

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(text = "Your monthly distance goal: " + (sliderPosition.value).roundToInt() + " km")
            },
            text = {
                Slider(
                    value = sliderPosition.value,
                    onValueChange = {

                        sliderPosition.value = it
                        it.toInt()
                    },
                    valueRange = 1f..100f,
                    onValueChangeFinished = {
                        // launch some business logic update with the state you hold
                        // viewModel.updateSelectedSliderValue(sliderPosition)
                    },
                    steps = 0,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondary,
                        activeTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        viewModel.saveMonthlyGoal((sliderPosition.value).roundToInt())
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MonthlyGoalComponentPreview() {
    MonthlyGoalComponent()
}