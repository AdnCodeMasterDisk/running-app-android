package com.runningapp.app.domain.use_case

import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.LoginRequest
import com.runningapp.app.data.remote.dto.toLoginResponse
import com.runningapp.app.domain.model.LoginResponse
import com.runningapp.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(requestBody: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val user = repository.loginUser(requestBody).toLoginResponse()
            emit(Resource.Success(user))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}