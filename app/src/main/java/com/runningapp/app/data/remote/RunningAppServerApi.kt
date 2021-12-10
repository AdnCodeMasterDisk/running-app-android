package com.runningapp.app.data.remote

import com.runningapp.app.data.remote.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface RunningAppServerApi {

  //  @FormUrlEncoded
    @POST("auth/signin")
    suspend fun loginUser(
       @Body requestBody : LoginRequestDTO
    ): UserDTO

    @POST("auth/signup")
    suspend fun registerUser(
        @Body requestBody : RegisterRequestDTO
    ): ResponseBody

    @GET("activities")
    suspend fun getAllRuns(): List<RunDTO>

    @GET("activities/user/{userId}")
    suspend fun getUserRuns(@Path("userId") userId: Int): List<RunDTO>

    @Multipart
    @POST("add-activity")
    suspend fun saveRunToDb(
      @Part mapFile : MultipartBody.Part,
      @Part requestBody : MultipartBody.Part
    ): ResponseBody

}

