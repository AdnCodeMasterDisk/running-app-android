package com.runningapp.app.ui.utils

import com.runningapp.app.domain.model.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val error: String = ""
)