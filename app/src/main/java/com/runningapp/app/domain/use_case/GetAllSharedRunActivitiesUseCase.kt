package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.toRunActivity
import com.runningapp.app.domain.model.RunActivity
import com.runningapp.app.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllSharedRunActivitiesUseCase @Inject constructor(
    private val repository: RunRepository
) {
    operator fun invoke(): Flow<Resource<List<RunActivity>>> = flow {
        try {
            emit(Resource.Loading<List<RunActivity>>())
            val runs = repository.getAllSharedRunActivities().map { it.toRunActivity() }
            emit(Resource.Success<List<RunActivity>>(runs))
        } catch(e: HttpException) {
            emit(Resource.Error<List<RunActivity>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<RunActivity>>("Couldn't reach server. Check your internet connection."))
        }
    }
}