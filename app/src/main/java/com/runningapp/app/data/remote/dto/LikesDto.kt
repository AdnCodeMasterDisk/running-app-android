package com.runningapp.app.data.remote.dto

import com.runningapp.app.domain.model.Likes

data class LikesDto(
    val activityId: Int,
    val userId: Int
)

fun LikesDto.toLikes(): Likes {
    return Likes(
        activityId = activityId,
        userId = userId
    )
}