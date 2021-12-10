package com.runningapp.app.data.repository

import com.runningapp.app.data.remote.RunningAppServerApi
import com.runningapp.app.data.remote.dto.RunDTO
import com.runningapp.app.data.remote.dto.SaveRunRequestDTO
import com.runningapp.app.domain.repository.RunRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(
    private val api: RunningAppServerApi
) : RunRepository {

    override suspend fun getAllRuns(): List<RunDTO> {
        return api.getAllRuns()
    }

    override suspend fun getUserRuns(userId: Int): List<RunDTO> {
        return api.getUserRuns(userId)
    }

    override suspend fun saveRun(mapFile: MultipartBody.Part, requestBody: MultipartBody.Part): ResponseBody {
        return api.saveRunToDb(mapFile, requestBody)
    }

}