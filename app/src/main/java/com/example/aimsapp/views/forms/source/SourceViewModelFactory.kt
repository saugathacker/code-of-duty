package com.example.aimsapp.views.forms.source

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SourceViewModelFactory( private val application: Application):
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SourceViewModel::class.java)){
            return SourceViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}