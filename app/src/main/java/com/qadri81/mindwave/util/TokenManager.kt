package com.qadri81.mindwave.util

import android.content.Context
import com.qadri81.mindwave.util.Constants.PREFS_TOKEN_FILE
import com.qadri81.mindwave.util.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? = prefs.getString(USER_TOKEN, null)
}