package com.runningapp.app.domain.model

data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val token: String
)