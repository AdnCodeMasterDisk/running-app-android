package com.runningapp.app.domain.repository

import com.runningapp.app.data.remote.dto.LikesDto
import com.runningapp.app.data.remote.dto.RunActivityDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface RunRepository {

    suspend fun getAllRunActivities(): List<RunActivityDto>

    suspend fun getAllSharedRunActivities(): List<RunActivityDto>

    suspend fun getUserRunActivities(userId: Int): List<RunActivityDto>

    suspend fun changeRunActivityShareStatus(runActivityId: Int): ResponseBody

    suspend fun updateLikeRunActivity(userId: Int, activityId: Int): ResponseBody

    suspend fun saveRun(mapFile: MultipartBody.Part, requestBody: MultipartBody.Part): ResponseBody

    suspend fun getUserLikedPosts(userId: Int): List<LikesDto>
}
