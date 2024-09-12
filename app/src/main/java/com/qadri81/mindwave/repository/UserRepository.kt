package com.qadri81.mindwave.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qadri81.mindwave.api.UserApi
import com.qadri81.mindwave.models.UserRequest
import com.qadri81.mindwave.models.UserResponse
import com.qadri81.mindwave.util.Constants.TAG
import com.qadri81.mindwave.util.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
) {

    private val _userResponseLivedata = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLivedata: LiveData<NetworkResult<UserResponse>> get() = _userResponseLivedata

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLivedata.postValue(NetworkResult.Loading())
        val response = userApi.signUp(userRequest)
        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLivedata.postValue(NetworkResult.Loading())
        val response = userApi.signIn(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        try {
            if (response.isSuccessful && response.body() != null) {
                _userResponseLivedata.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorBody = JSONObject(response.errorBody()!!.charStream().readText())
                _userResponseLivedata.postValue(NetworkResult.Error(errorBody.getString("message")))
            } else {
                _userResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            _userResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}