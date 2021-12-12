package com.runningapp.app.data.remote

import com.runningapp.app.data.remote.dto.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface RunningAppServerApi {

    // Authentication

    @POST("auth/signin")
    suspend fun loginUser(
       @Body requestBody : LoginRequest
    ): LoginResponseDto

    @POST("auth/signup")
    suspend fun registerUser(
        @Body requestBody : RegisterRequest
    ): ResponseBody


    // Activities

    @GET("activities")
    suspend fun getAllRunActivities(): List<RunActivityDto>

    @GET("activities/query?search=isPosted:true")
    suspend fun getAllSharedRunActivities(): List<RunActivityDto>

    @GET("activities/user/{userId}")
    suspend fun getUserRunActivities(@Path("userId") userId: Int): List<RunActivityDto>

    @Multipart
    @POST("add-activity")
    suspend fun saveRunToDb(
      @Part mapFile : MultipartBody.Part,
      @Part requestBody : MultipartBody.Part
    ): ResponseBody

    @POST("update-like/user/{userId}/activity/{activityId}")
    suspend fun updateLikeRunActivity(@Path("userId") userId: Int, @Path("activityId") activityId: Int): ResponseBody

    @PUT("change-activity-post-status/{runActivityId}")
    suspend fun changeRunActivityShareStatus(@Path("runActivityId") runActivityId: Int): ResponseBody

    @GET("likes/user/{userId}")
    suspend fun getUserLikedPosts(@Path("userId") userId: Int): List<LikesDto>
}


