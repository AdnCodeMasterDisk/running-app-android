package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserMonthlyDistanceUseCase @Inject constructor(
    private val repository: RunRepository
) {

    operator fun invoke(userId: Int): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading<Long>())
            val userMonthlyDistance = repository.getUserMonthlyDistance(userId)
            emit(Resource.Success<Long>(userMonthlyDistance))
        } catch(e: HttpException) {
            emit(Resource.Error<Long>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<Long>("Couldn't reach server. Check your internet connection."))
        }
    }
}