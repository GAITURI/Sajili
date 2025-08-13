package com.mark.data

import retrofit2.http.GET

interface Profile {


    @GET("api/profile/profile")
    suspend fun getUserProfile():User
}
