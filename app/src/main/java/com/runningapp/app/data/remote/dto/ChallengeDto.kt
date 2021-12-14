package com.runningapp.app.data.remote.dto

import com.runningapp.app.domain.model.Challenge

data class ChallengeDto(
    val amountToComplete: Double,
    val description: String,
    val endDate: String,
    val id: Int,
    val name: String,
    val startDate: String,
    val participantsAmount: Int
)

fun ChallengeDto.toChallenge(): Challenge {
    return Challenge(
        id = id,
        amountToComplete = amountToComplete,
        description = description,
        endDate = endDate,
        name = name,
        startDate = startDate,
        participantsAmount = participantsAmount
    )
}