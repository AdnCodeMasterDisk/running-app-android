package com.runningapp.app.ui.screens

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.runningapp.app.service.TrackingService
import com.runningapp.app.ui.theme.custom_color_red
import com.runningapp.app.ui.theme.custom_color_yellow
import com.runningapp.app.ui.utils.Constants.ACTION_PAUSE_SERVICE
import com.runningapp.app.ui.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import com.runningapp.app.ui.utils.Constants.ACTION_STOP_SERVICE
import com.runningapp.app.ui.utils.Constants.MAP_ZOOM
import com.runningapp.app.ui.utils.Constants.POLYLINE_COLOR
import com.runningapp.app.ui.utils.Constants.POLYLINE_WIDTH
import com.runningapp.app.ui.utils.TrackingUtility
import com.runningapp.app.ui.map.rememberMapViewWithLifecycle
import com.runningapp.app.ui.utils.Constants.POLYLINE_COLOR2
import java.math.BigDecimal
import java.math.RoundingMode

private var isTracking: Boolean = false
private var pathPoints = mutableListOf<LatLng>()
private var pathPointsAll = mutableListOf<LatLng>()
private var map: GoogleMap? = null
private var curTimeInMillis: Long = 0L
private lateinit var context: Context
private var distanceInMeters = 0

@Composable
fun RunActivityScreen() {
    val totalTime = remember { mutableStateOf("00:00:00:00") }
    val distance = remember { mutableStateOf(0.0) }
    val avgSpeed = remember { mutableStateOf(0.0) }
    val caloriesBurned = remember { mutableStateOf(0) }
    val pace = remember { mutableStateOf("00'00''") }

    TrackingService.isTracking.observe(LocalLifecycleOwner.current, Observer {
        updateTracking(it)
    })
    TrackingService.pathPoints.observe(LocalLifecycleOwner.current, Observer {
        // TODO: dlaczego obserwuje size razy??? Napraw!
        if (it.isNotEmpty() && (pathPoints.isEmpty() || pathPoints.last() != it.last())) {
            pathPoints.add(it.last())
            if (pathPoints.size != it.size) {
                println("pp size: " + pathPoints.size + ",  it size" + it.size)
                pathPoints.clear()
                pathPoints.addAll(it)
                addAllPolylines()
                distanceInMeters = TrackingUtility.calculatePolylineLength(pathPoints).toInt()
            } else {
                addLatestPolyline()
            }

            moveCameraToUser()

            val distanceTemp = ((distanceInMeters / 1000f).toDouble())
            distance.value = BigDecimal(distanceTemp).setScale(2, RoundingMode.HALF_EVEN).toDouble()

            caloriesBurned.value = ((distance.value) * 80).toInt()

        }
    })

    TrackingService.timeRunInMillis.observe(LocalLifecycleOwner.current, Observer {
        curTimeInMillis = it
        val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeInMillis, true)
        totalTime.value = formattedTime

        val speedTemp = ((distance.value) / (curTimeInMillis / 1000f / 60 / 60) * 10) / 10f
        if (!speedTemp.isNaN() && !speedTemp.isInfinite())
            avgSpeed.value = BigDecimal(speedTemp).setScale(1, RoundingMode.HALF_EVEN).toDouble()

        val minutes = ((curTimeInMillis / 1000f / 60) * 10) / 10f
        val minutesForOneKm = minutes / distance.value
        if (!minutesForOneKm.isNaN() && !minutesForOneKm.isInfinite())
            pace.value = BigDecimal(minutesForOneKm).setScale(2, RoundingMode.HALF_EVEN).toString()
    })

    context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = distance.value.toString() + " km",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp, 40.dp, 40.dp, 40.dp))
            ) {
                val mapViewWithLifecycle = rememberMapViewWithLifecycle()

                AndroidView(factory = { mapViewWithLifecycle }) { mapView ->
                    mapView.getMapAsync {
                        map = it
                        map!!.isMyLocationEnabled = true
                      ///  moveCameraToUser()

                    }
                }
            }

            Button(
                onClick = { stopRun() },
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .align(Alignment.BottomStart)
                    .size(75.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = custom_color_red)
            ) {
                Icon(
                    Icons.Filled.Stop,
                    contentDescription = "Stop",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
            }

            Button(
                onClick = { toggleRun() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(100.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = custom_color_yellow)
            ) {
                Icon(
                    Icons.Filled.Pause,
                    contentDescription = "Pause",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp)),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Timer, "Timer")

                Column(
                    modifier = Modifier.padding(start = 24.dp)
                ) {
                    Text(
                        text = totalTime.value,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Total time",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp)),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.DirectionsRun, "Pace")

                Column(
                    modifier = Modifier.padding(start = 24.dp)
                ) {
                    Text(
                        text = pace.value,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Average pace",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.LocalFireDepartment, "Calories", tint = custom_color_yellow)

                    Column(
                        modifier = Modifier.padding(start = 24.dp)
                    ) {
                        Text(
                            text = caloriesBurned.value.toString(),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Calories",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Speed, "Speed", tint = custom_color_red)

                    Column(
                        modifier = Modifier.padding(start = 24.dp)
                    ) {
                        Text(
                            text = avgSpeed.value.toString() + " km/h",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Average speed",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(text = "Running activity")
            },
            text = {
                Text(
                    "You can start whenever you're ready"
                )
            },
            confirmButton = {
                FilledTonalButton(
                    onClick = {
                        openDialog.value = false
                        toggleRun()
                    }
                ) {
                    Text("Start")
                }
            },
            dismissButton = {
                }
        )
    }
}

private fun toggleRun() {
    println("toggle run")
    if (isTracking) {
        sendCommandToService(ACTION_PAUSE_SERVICE)
    } else {
        sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
    }
}

private fun stopRun() {
    sendCommandToService(ACTION_STOP_SERVICE)
    //findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
}

private fun updateTracking(isTracking: Boolean) {
    //this.isTracking = isTracking
    //  if(!isTracking) {
    //  btnToggleRun.text = "Start"
    //  btnFinishRun.visibility = View.VISIBLE
    // } else {
    //   btnToggleRun.text = "Stop"
    //   menu?.getItem(0)?.isVisible = true
    //   btnFinishRun.visibility = View.GONE
    //   }
}

private fun moveCameraToUser() {
    if (pathPoints.isNotEmpty()) {
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                pathPoints.last(),
                MAP_ZOOM
            )
        )
    }
}

private fun addAllPolylines() {
    val polylineOptions = PolylineOptions()
        .color(POLYLINE_COLOR)
        .width(POLYLINE_WIDTH)
        .addAll(pathPoints)
    map?.addPolyline(polylineOptions)
}

private fun addLatestPolyline() {
    if (pathPoints.isNotEmpty() && pathPoints.size > 1) {
        val preLastLatLng = pathPoints[pathPoints.size - 2]
        val lastLatLng = pathPoints.last()
        val polylineOptions = PolylineOptions()
            .color(POLYLINE_COLOR)
            .width(POLYLINE_WIDTH)
            .add(preLastLatLng)
            .add(lastLatLng)
        map?.addPolyline(polylineOptions)

        val result = FloatArray(1)
        Location.distanceBetween(
            preLastLatLng.latitude,
            preLastLatLng.longitude,
            lastLatLng.latitude,
            lastLatLng.longitude,
            result
        )
        distanceInMeters += result[0].toInt()
        println("Last distance in meters: " + result[0].toInt())
        println("Distance in meters: $distanceInMeters")
    }
}

private fun sendCommandToService(action: String) =
    Intent(context, TrackingService::class.java).also {
        it.action = action
        context.startService(it)
    }

@Preview(showBackground = true)
@Composable
fun RunActivityScreenPreview() {
    RunActivityScreen()
}

