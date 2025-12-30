package com.mark.data

data class RegisterRequest(
    val idToken:String,
    val phoneNumber:String,
    val pin:String,
    val confirmPin:String
)