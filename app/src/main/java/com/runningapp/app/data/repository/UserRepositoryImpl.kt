package com.runningapp.app.data.repository

import com.google.gson.JsonObject
import com.runningapp.app.data.remote.RunningAppServerApi
import com.runningapp.app.data.remote.dto.LoginRequestDTO
import com.runningapp.app.data.remote.dto.RegisterRequestDTO
import com.runningapp.app.data.remote.dto.UserDTO
import com.runningapp.app.domain.repository.UserRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: RunningAppServerApi
) : UserRepository {

    override suspend fun loginUser(requestBody: LoginRequestDTO): UserDTO {
        return api.loginUser(requestBody)
    }

    override suspend fun registerUser(requestBody: RegisterRequestDTO): ResponseBody {
        return api.registerUser(requestBody)
    }
}