package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.toChallenge
import com.runningapp.app.data.remote.dto.toUserChallenges
import com.runningapp.app.domain.model.Challenge
import com.runningapp.app.domain.model.UserChallenges
import com.runningapp.app.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllUserChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<List<UserChallenges>>> = flow {
        try {
            emit(Resource.Loading<List<UserChallenges>>())
            val challenges = repository.getAllUserChallenges(userId).map { it.toUserChallenges() }
            emit(Resource.Success<List<UserChallenges>>(challenges))
        } catch(e: HttpException) {
            emit(Resource.Error<List<UserChallenges>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<UserChallenges>>("Couldn't reach server. Check your internet connection."))
        }
    }
}