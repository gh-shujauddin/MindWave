package com.qadri81.mindwave.api

import com.qadri81.mindwave.models.UserRequest
import com.qadri81.mindwave.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/users/signup")
    suspend fun signUp(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("/users/signin")
    suspend fun signIn(@Body userRequest: UserRequest): Response<UserResponse>


}