package com.example.aimsapp.views.currentTrip

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.database.tripDatabase.TripDao

class CurrentTripViewModelFactory(private val dataSource: TripDao, private val application: Application ):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentTripViewModel::class.java)){
            return CurrentTripViewModel(dataSource,application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}