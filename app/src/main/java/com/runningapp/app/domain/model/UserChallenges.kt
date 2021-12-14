package com.runningapp.app.domain.model

import com.runningapp.app.data.remote.dto.Challenge
import com.runningapp.app.data.remote.dto.Id
import com.runningapp.app.data.remote.dto.User

data class UserChallenges(
    val challenge: Challenge,
    val currentAmount: Int,
    val id: Id,
    val isCompleted: Boolean,
    val user: User
)