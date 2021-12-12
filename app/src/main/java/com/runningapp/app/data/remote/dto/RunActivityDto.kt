package com.runningapp.app.data.remote.dto

import com.runningapp.app.domain.model.RunActivity

data class RunActivityDto(
    val calories: Int,
    val date: String,
    val distance: Int,
    val id: Int,
    val isPosted: Boolean,
    val likesAmount: Int,
    val mapImage: MapImage,
    val pace: String,
    val speed: Double,
    val totalTime: String,
    val user: User
)

fun RunActivityDto.toRunActivity(): RunActivity {
    return RunActivity(
        id = id,
        date = date,
        distance = distance,
        likesAmount = likesAmount,
        mapImage = mapImage,
        pace = pace,
        speed = speed,
        totalTime = totalTime,
        user = user,
        isPosted = isPosted,
        calories = calories
    )
}