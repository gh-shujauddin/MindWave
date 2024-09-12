package com.qadri81.mindwave.api

import android.util.Log
import com.qadri81.mindwave.util.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = tokenManager.getToken()
        Log.d("TOKE", token.toString())
        request.addHeader("Authorization", "Bearar $token")

        return chain.proceed(request.build())
    }
}