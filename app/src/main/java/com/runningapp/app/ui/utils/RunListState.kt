package com.runningapp.app.ui.utils

import com.runningapp.app.domain.model.Run

data class RunListState(
    val isLoading: Boolean = false,
    val runs: List<Run> = emptyList(),
    val error: String = ""
)