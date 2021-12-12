package com.runningapp.app.ui.utils

import com.runningapp.app.domain.model.RunActivity

data class RunListState(
    val isLoading: Boolean = false,
    val runs: List<RunActivity> = emptyList(),
    val error: String = ""
)