package com.mark.ui.CustomerDashboard

import com.mark.data.User

sealed class ProfileUiState{
    data object Loading:ProfileUiState()
    data class Success(val profile: User):ProfileUiState()
    data class Error(val message: String):ProfileUiState()

}