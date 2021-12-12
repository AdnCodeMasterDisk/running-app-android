package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.RegisterRequest
import com.runningapp.app.domain.repository.AuthRepository
import com.runningapp.app.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShareRunActivityUseCase @Inject constructor(
    private val repository: RunRepository
) {
    operator fun invoke(runId: Int):Flow<Resource<ResponseBody>> = flow {
        try {
            emit(Resource.Loading())
            val responseBody = repository.changeRunActivityShareStatus(runId)
            emit(Resource.Success(responseBody))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}