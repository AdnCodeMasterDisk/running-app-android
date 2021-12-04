package com.runningapp.app.domain.use_case

import com.google.gson.JsonObject
import com.runningapp.app.common.Resource
import com.runningapp.app.data.remote.dto.LoginRequestDTO
import com.runningapp.app.data.remote.dto.toUser
import com.runningapp.app.domain.model.User
import com.runningapp.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(requestBody: LoginRequestDTO): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val user = repository.loginUser(requestBody).toUser()
            emit(Resource.Success(user))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}