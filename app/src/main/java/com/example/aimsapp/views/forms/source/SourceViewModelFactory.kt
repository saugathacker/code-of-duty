package com.example.aimsapp.views.forms.source

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.database.tripDatabase.WayPoint

/**
 * Constructor for source view model
 */
class SourceViewModelFactory(private val application: Application, private val wayPoint: WayPoint) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SourceViewModel::class.java)) {
            return SourceViewModel(application, wayPoint) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}