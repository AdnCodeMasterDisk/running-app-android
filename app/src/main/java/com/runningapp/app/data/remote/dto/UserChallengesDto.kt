package com.runningapp.app.data.remote.dto

import com.runningapp.app.domain.model.UserChallenges

data class UserChallengesDto(
    val challenge: Challenge,
    val completeDate: String,
    val currentAmount: Int,
    val id: Int,
    val isCompleted: Boolean,
    val joinDate: String,
    val user: User
)

fun UserChallengesDto.toUserChallenges(): UserChallenges {
    return UserChallenges(
        id = id,
        challenge = challenge,
        currentAmount = currentAmount,
        isCompleted = isCompleted,
        user = user
    )
}