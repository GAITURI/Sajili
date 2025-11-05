package com.mark.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

//interface for authentication-related API calls to the Spring Boot backend
interface  AuthService {
    @POST("api/auth/verify_token")
    suspend fun verifyToken(@Body request: FirebaseTokenRequest):Response<AuthResponse>

    @GET("api/auth/protected-data")
    suspend fun getProtectedData(@Header("Authorization") token:String):Response<MessageResponse>

}