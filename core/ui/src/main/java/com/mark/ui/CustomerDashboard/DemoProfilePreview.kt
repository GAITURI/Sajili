package com.mark.ui.CustomerDashboard

import com.mark.data.Profile
import com.mark.data.User
import kotlinx.coroutines.delay
//a fake implementation for use in Jetpack Compose @Preview functions
//it overrides getUserProfile() to return specific hardcoded user data for the preview
class DemoProfilePreview :Profile {
    override suspend fun getUserProfile():User{
        delay(500)
        return User(name="Preview User", phoneNumber="0791860878")
    }
}