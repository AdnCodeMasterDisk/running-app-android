package com.runningapp.app.ui.utils

import okhttp3.ResponseBody

data class SaveRunState(
    val isLoading: Boolean = false,
    val responseBody: ResponseBody? = null,
    val error: String = ""
)