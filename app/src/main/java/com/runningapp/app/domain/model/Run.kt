package com.runningapp.app.domain.model

data class Run(
    val calories: Int,
    val date: String,
    val distance: Int,
    val id: Int,
    val mapImage: Any?,
    val pace: String,
    val speed: Double,
    val totalTime: String
)