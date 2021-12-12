package com.runningapp.app.domain.model

import com.runningapp.app.data.remote.dto.MapImage
import com.runningapp.app.data.remote.dto.User

data class RunActivity (
    val date: String,
    val distance: Int,
    val id: Int,
    val likesAmount: Int,
    val mapImage: MapImage,
    val pace: String,
    val speed: Double,
    val totalTime: String,
    val user: User,
    val isPosted: Boolean,
    val calories: Int
    )