package com.runningapp.app.domain.model

data class Challenge(
    val amountToComplete: Double,
    val description: String,
    val endDate: String,
    val id: Int,
    val name: String,
    val startDate: String,
    val participantsAmount: Int
)