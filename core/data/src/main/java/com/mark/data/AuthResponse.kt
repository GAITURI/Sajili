package com.mark.data

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val jwt:String?=null,
    val phoneNumber: String?=null
)

data class ErrorResponse(
    val message:String,
    val timestamp:String?=null,
    val status:Int?= null,
    val error:String?=null,
    val path:String? = null

)
data class UserProfile(
    val id: String,
    val phoneNumber: String,
)