package com.runningapp.app.ui.utils

import okhttp3.ResponseBody

data class RegisterState(
    val isLoading: Boolean = false,
    val responseBody: ResponseBody? = null,
    val error: String = ""
)