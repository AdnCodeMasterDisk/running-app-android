package com.runningapp.app.data.repository

import com.runningapp.app.data.remote.RunningAppServerApi
import com.runningapp.app.data.remote.dto.LikesDto
import com.runningapp.app.data.remote.dto.RunActivityDto
import com.runningapp.app.domain.repository.RunRepository
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Path
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(
    private val api: RunningAppServerApi
) : RunRepository {

    override suspend fun getAllRunActivities(): List<RunActivityDto> {
        return api.getAllRunActivities()
    }

    override suspend fun getAllSharedRunActivities(): List<RunActivityDto> {
        return api.getAllSharedRunActivities()
    }

    override suspend fun changeRunActivityShareStatus(runActivityId: Int): ResponseBody {
        return api.changeRunActivityShareStatus(runActivityId)
    }

    override suspend fun getUserRunActivities(userId: Int): List<RunActivityDto> {
        return api.getUserRunActivities(userId)
    }

    override suspend fun updateLikeRunActivity(userId: Int, activityId: Int): ResponseBody {
        return api.updateLikeRunActivity(userId, activityId)
    }

    override suspend fun saveRun(mapFile: MultipartBody.Part, requestBody: MultipartBody.Part): ResponseBody {
        return api.saveRunToDb(mapFile, requestBody)
    }

    override suspend fun getUserLikedPosts(userId: Int): List<LikesDto> {
        return api.getUserLikedPosts(userId)
    }

    override suspend fun getUserMonthlyDistance(userId: Int): Long {
        return api.getUserMonthlyDistance(userId)
    }

}