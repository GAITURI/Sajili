package com.mark.ui.CustomerDashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mark.data.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CustomerProfileViewModel(
    private val profileService: Profile
): ViewModel(){
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState
    init {
        fetchUserProfile()
    }
    private fun fetchUserProfile(){
        viewModelScope.launch {
            try{
                val profilePhone = profileService.getUserProfile()
                _uiState.value= ProfileUiState.Success(profilePhone)
            }catch (e:Exception){
                _uiState.value= ProfileUiState.Error("Failed to fetch profile:${e.message}")
            }
        }
    }
}