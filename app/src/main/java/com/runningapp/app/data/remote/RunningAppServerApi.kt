package com.runningapp.app.data.remote

import com.google.gson.JsonObject
import com.runningapp.app.data.remote.dto.LoginRequestDTO
import com.runningapp.app.data.remote.dto.RegisterRequestDTO
import com.runningapp.app.data.remote.dto.UserDTO
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

}