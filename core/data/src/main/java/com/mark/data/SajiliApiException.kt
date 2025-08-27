package com.mark.data

class SajiliApiException(
    message:String,
    val statusCode:Int? = null,
    val errorBody:ErrorResponse?= null,
    cause:Throwable?= null
):Exception(message, cause)