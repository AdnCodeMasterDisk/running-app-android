package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.RegisterRequest
import com.runningapp.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(requestBody: RegisterRequest):Flow<Resource<ResponseBody>> = flow {
        try {
            emit(Resource.Loading())
            val responseBody = repository.registerUser(requestBody)
            emit(Resource.Success(responseBody))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}