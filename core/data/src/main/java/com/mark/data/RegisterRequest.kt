package com.mark.data

data class RegisterRequest(
    val phoneNumber:String,
    val pin:String,
    val confirmPin:String
)