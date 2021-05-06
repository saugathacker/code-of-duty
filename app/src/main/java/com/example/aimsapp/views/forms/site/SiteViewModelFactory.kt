package com.example.aimsapp.views.forms.site

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.database.tripDatabase.WayPoint

/**
 * Constructor for Site view model
 */
class SiteViewModelFactory(private val application: Application, private val wayPoint: WayPoint): ViewModelProvider.Factory{
        @Suppress("unchecked_cast")

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SiteViewModel::class.java)){
                return SiteViewModel(application, wayPoint) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
}