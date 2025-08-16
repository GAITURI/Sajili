package com.mark.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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