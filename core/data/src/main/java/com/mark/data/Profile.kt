package com.mark.data

import retrofit2.http.GET
//an interface defininf the contract for fetching a user profile
interface Profile {


    @GET("api/profile/profile") //network calls
    suspend fun getUserProfile():User //method
}
