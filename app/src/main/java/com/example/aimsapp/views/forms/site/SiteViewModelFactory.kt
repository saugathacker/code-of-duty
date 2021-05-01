package com.example.aimsapp.views.forms.site

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SiteViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        @Suppress("unchecked_cast")

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SiteViewModel::class.java)){
                return SiteViewModel(application) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
}