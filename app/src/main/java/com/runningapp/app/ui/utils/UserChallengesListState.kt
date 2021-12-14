package com.runningapp.app.ui.utils

import com.runningapp.app.domain.model.UserChallenges

data class UserChallengesListState(
    val isLoading: Boolean = false,
    val userChallenges: List<UserChallenges> = emptyList(),
    val error: String = ""
) {
}