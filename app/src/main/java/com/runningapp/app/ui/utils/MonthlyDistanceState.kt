package com.runningapp.app.ui.utils

data class MonthlyDistanceState(
    val isLoading: Boolean = false,
    val monthlyDistance: Long? = null,
    val error: String = ""
)