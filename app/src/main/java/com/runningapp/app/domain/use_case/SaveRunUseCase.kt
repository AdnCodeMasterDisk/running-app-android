package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.LoginRequestDTO
import com.runningapp.app.data.remote.dto.RegisterRequestDTO
import com.runningapp.app.data.remote.dto.SaveRunRequestDTO
import com.runningapp.app.data.remote.dto.toUser
import com.runningapp.app.domain.model.User
import com.runningapp.app.domain.repository.RunRepository
import com.runningapp.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SaveRunUseCase @Inject constructor(
    private val repository: RunRepository
) {
    operator fun invoke(mapFile: MultipartBody.Part, requestBody: MultipartBody.Part):Flow<Resource<ResponseBody>> = flow {
        try {
            emit(Resource.Loading())
            val responseBody = repository.saveRun(mapFile, requestBody)
            emit(Resource.Success(responseBody))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}