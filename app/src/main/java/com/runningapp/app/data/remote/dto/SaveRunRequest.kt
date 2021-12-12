package com.runningapp.app.data.remote.dto

data class SaveRunRequest (
    val userId: Long,
    val totalTime: String,
    val calories: Int,
    val distance: Int,
    val pace: String?,
    val speed: Float?
)