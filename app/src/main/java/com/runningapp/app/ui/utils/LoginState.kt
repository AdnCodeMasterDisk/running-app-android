package com.runningapp.app.ui.utils

import com.runningapp.app.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)