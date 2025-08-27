package com.mark.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

//to insatiate an interface, hilt needs to know how to provide a concrete class that implements the TokenRepository interface
@Singleton
class SharedPreferencesTokenRepository @Inject constructor(private val sharedPreferences: SharedPreferences):TokenRepository{
    private companion object{
         const val KEY_AUTH_TOKEN="auth_token"
    }
    override fun getToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN,token).apply()
    }

    override fun clearToken() {
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply()
    }

}

//the abstract class below binds/links tokenrepository to shared preferencetoken repository
