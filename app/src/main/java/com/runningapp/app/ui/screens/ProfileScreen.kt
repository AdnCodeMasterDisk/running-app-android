package com.runningapp.app.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.EmojiEvents
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
import com.runningapp.app.ui.components.*
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.theme.custom_color_yellow
import com.runningapp.app.ui.utils.SimpleListDataItem
import com.runningapp.app.ui.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val expanded = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MonthlyGoalComponent()
            }
        }

        item {
            val userId = viewModel.userId.observeAsState()

            LaunchedEffect(key1 = true) {
                userId.value?.let { viewModel.getAllUserRunActivities(it) }
            }

            UserChallengesLazyRow()

            val state = viewModel.state.value

            Text(
                text = "Last trainings",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
            )

            if (state.isLoading) {
                ShimmerAnimation("activity")
            }

            if (expanded.value) {
                if (state.runs.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillParentMaxHeight()) {
                        items(state.runs) { run ->
                            UserTrainingActivityCard(runActivity = run)
                        }
                    }
                }
            } else {
                if (state.runs.isNotEmpty()) {
                    UserTrainingActivityCard(runActivity = state.runs[0])
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
            if (state.error.isNotBlank()) {
                val errorMsg = if (state.error == "HTTP 404 ") "You have no trainings saved"
                else state.error
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
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    //val dataItems = (0..10).map { SimpleListDataItem("30 km in October") }
    //ProfileScreen(modifier = Modifier.fillMaxSize(), dataItems)
}