package com.runningapp.app.data.remote.dto

import com.runningapp.app.domain.model.Run

data class RunDTO(
    val calories: Int,
    val date: String,
    val distance: Int,
    val id: Int,
    val mapImage: Any?,
    val pace: String,
    val speed: Double,
    val totalTime: String
)

fun RunDTO.toRun(): Run {
    return Run(
         calories = calories,
         date = date,
         distance = distance,
         id = id,
         mapImage = mapImage,
         pace = pace,
         speed = speed,
         totalTime = totalTime
    )
}