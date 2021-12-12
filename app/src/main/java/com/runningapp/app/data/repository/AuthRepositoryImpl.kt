package com.runningapp.app.data.repository

import com.runningapp.app.data.remote.RunningAppServerApi
import com.runningapp.app.data.remote.dto.LoginRequest
import com.runningapp.app.data.remote.dto.LoginResponseDto
import com.runningapp.app.data.remote.dto.RegisterRequest
import com.runningapp.app.domain.repository.AuthRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: RunningAppServerApi
) : AuthRepository {

    override suspend fun loginUser(requestBody: LoginRequest): LoginResponseDto {
        return api.loginUser(requestBody)
    }

    override suspend fun registerUser(requestBody: RegisterRequest): ResponseBody {
        return api.registerUser(requestBody)
    }
}