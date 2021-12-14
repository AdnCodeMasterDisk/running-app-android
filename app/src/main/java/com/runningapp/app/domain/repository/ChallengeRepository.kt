package com.runningapp.app.domain.repository

import com.runningapp.app.data.remote.dto.ChallengeDto
import com.runningapp.app.data.remote.dto.UserChallengesDto
import okhttp3.ResponseBody

interface ChallengeRepository {
    suspend fun getAllChallenges(): List<ChallengeDto>

    suspend fun getAllUserChallenges(userId: Int): List<UserChallengesDto>

    suspend fun joinChallenge(userId: Int, challengeId: Int): ResponseBody
}