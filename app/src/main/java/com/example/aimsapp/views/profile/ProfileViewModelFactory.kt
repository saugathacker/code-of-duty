package com.example.aimsapp.views.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Constructor for Profile View Model
 */
class ProfileViewModelFactory(private val application: Application):
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}