package com.runningapp.app.data.repository

import com.runningapp.app.data.remote.RunningAppServerApi
import com.runningapp.app.data.remote.dto.ChallengeDto
import com.runningapp.app.data.remote.dto.UserChallengesDto
import com.runningapp.app.domain.repository.ChallengeRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
        private val api: RunningAppServerApi
    ) : ChallengeRepository {

    override suspend fun getAllChallenges(): List<ChallengeDto> {
        return api.getAllChallenges()
    }

    override suspend fun getAllUserChallenges(userId: Int): List<UserChallengesDto> {
        return api.getAllUserChallenges(userId)
    }

    override suspend fun joinChallenge(userId: Int, challengeId: Int): ResponseBody {
        return api.joinChallenge(userId, challengeId)
    }

}