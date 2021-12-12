package com.runningapp.app.data.remote.dto

data class RegisterRequest (
    val username: String,
    val email: String,
    val plainPassword: String
)