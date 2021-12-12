package com.runningapp.app.domain.repository

import com.runningapp.app.data.remote.dto.LoginRequest
import com.runningapp.app.data.remote.dto.LoginResponseDto
import com.runningapp.app.data.remote.dto.RegisterRequest
import okhttp3.ResponseBody

interface AuthRepository {
    suspend fun loginUser(requestBody : LoginRequest): LoginResponseDto

    suspend fun registerUser(requestBody : RegisterRequest): ResponseBody
}
