package com.mark.data

interface TokenRepository {
    fun getToken():String?
    fun saveToken(token:String)
    fun clearToken()
}