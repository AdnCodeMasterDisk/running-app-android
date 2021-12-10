package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.toRun
import com.runningapp.app.domain.model.Run
import com.runningapp.app.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllRunsUseCase @Inject constructor(
    private val repository: RunRepository
) {
    operator fun invoke(): Flow<Resource<List<Run>>> = flow {
        try {
            emit(Resource.Loading<List<Run>>())
            val runs = repository.getAllRuns().map { it.toRun() }
            emit(Resource.Success<List<Run>>(runs))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Run>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Run>>("Couldn't reach server. Check your internet connection."))
        }
    }
}