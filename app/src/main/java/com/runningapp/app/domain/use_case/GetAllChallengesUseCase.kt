package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.toChallenge
import com.runningapp.app.domain.model.Challenge
import com.runningapp.app.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(): Flow<Resource<List<Challenge>>> = flow {
        try {
            emit(Resource.Loading<List<Challenge>>())
            val challenges = repository.getAllChallenges().map { it.toChallenge() }
            emit(Resource.Success<List<Challenge>>(challenges))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Challenge>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Challenge>>("Couldn't reach server. Check your internet connection."))
        }
    }
}