package com.mark.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

//interface for authentication-related API calls to the Spring Boot backend
//a contract describing how the app talks to the springboot backend
interface  AuthService {
    @POST("api/auth/verify_token")
    suspend fun verifyToken(@Body request: FirebaseTokenRequest):Response<AuthResponse>

    @GET("api/auth/protected-data")
    suspend fun getProtectedData(@Header("Authorization") token:String):Response<MessageResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request:RegisterRequest):Response<AuthResponse>
}

// @POST("api/auth/verify_token")
//    suspend fun verifyToken(@Body request: FirebaseTokenRequest):Response<AuthResponse>
//sends a POST request to BASE_URL/api/verify_token
//sends a JSON body created from FirebaseTokenRequest
//receives an HTTP response containing AuthResponse
//its a suspend function meaning it runs inside a Kotlin coroutine
