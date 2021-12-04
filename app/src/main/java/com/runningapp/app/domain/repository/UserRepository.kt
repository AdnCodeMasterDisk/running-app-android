package com.runningapp.app.domain.repository

import com.google.gson.JsonObject
import com.runningapp.app.data.remote.dto.LoginRequestDTO
import com.runningapp.app.data.remote.dto.RegisterRequestDTO
import com.runningapp.app.data.remote.dto.UserDTO
import okhttp3.ResponseBody

interface UserRepository {
    suspend fun loginUser(requestBody : LoginRequestDTO): UserDTO

    suspend fun registerUser(requestBody : RegisterRequestDTO): ResponseBody
}
