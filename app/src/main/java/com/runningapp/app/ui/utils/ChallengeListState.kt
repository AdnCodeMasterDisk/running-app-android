package com.runningapp.app.ui.utils

import com.runningapp.app.domain.model.Challenge

data class ChallengeListState(
    val isLoading: Boolean = false,
    val challenges: List<Challenge> = emptyList(),
    val error: String = ""
)