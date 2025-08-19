package com.mark.ui.CustomerDashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.data.Profile

class CustomerProfileViewModelFactory(
    private val profileService: Profile

):ViewModelProvider.Factory{
    override fun <T:ViewModel>create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(CustomerProfileViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return  CustomerProfileViewModel(profileService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}