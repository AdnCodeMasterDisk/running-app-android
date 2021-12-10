package com.runningapp.app.domain.repository

import com.runningapp.app.data.remote.dto.RunDTO
import com.runningapp.app.data.remote.dto.SaveRunRequestDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface RunRepository {

    suspend fun getAllRuns(): List<RunDTO>

    suspend fun getUserRuns(userId: Int): List<RunDTO>

    suspend fun saveRun(mapFile: MultipartBody.Part, requestBody: MultipartBody.Part): ResponseBody

}
