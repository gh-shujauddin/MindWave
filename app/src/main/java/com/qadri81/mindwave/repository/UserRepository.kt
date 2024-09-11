package com.qadri81.mindwave.repository

import android.util.Log
import com.qadri81.mindwave.api.UserApi
import com.qadri81.mindwave.models.UserRequest
import com.qadri81.mindwave.util.Constants.TAG
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun registerUser(userRequest: UserRequest) {
        val response = userApi.signUp(userRequest)
        Log.d(TAG, response.body().toString())
    }

    suspend fun loginUser(userRequest: UserRequest) {
        val response = userApi.signIn(userRequest)
        Log.d(TAG, response.body().toString())

    }
}