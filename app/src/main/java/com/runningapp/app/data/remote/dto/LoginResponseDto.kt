package com.runningapp.app.data.remote.dto

import com.runningapp.app.domain.model.LoginResponse

data class LoginResponseDto(
    val email: String,
    val id: Int,
    val token: String,
    val type: String,
    val username: String
)

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        id = id,
        username = username,
        email = email,
        token = token
    )
}