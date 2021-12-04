package com.runningapp.app.data.remote.dto

import com.runningapp.app.domain.model.User

data class UserDTO(
    val email: String,
    val id: Int,
    val token: String,
    val type: String,
    val username: String
)

fun UserDTO.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        token = token
    )
}