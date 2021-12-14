package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LeaveChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(userId: Int, challengeId: Int): Flow<Resource<ResponseBody>> = flow {
        try {
            emit(Resource.Loading())
            val responseBody = repository.leaveChallenge(userId, challengeId)
            emit(Resource.Success(responseBody))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}