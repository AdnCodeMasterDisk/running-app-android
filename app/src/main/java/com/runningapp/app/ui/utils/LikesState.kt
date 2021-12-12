package com.runningapp.app.ui.utils

import com.runningapp.app.domain.model.Likes

data class LikesState(
    val isLoading: Boolean = false,
    var likedActivities: List<Likes> = emptyList(),
    val error: String = ""
)