package com.mark.data

open class ProfileServiceImpl():Profile{
    override suspend fun getUserProfile():User{
//        return Profile.getUserProfile()
        throw NotImplementedError("Real implementation using network service needed")
    }
}