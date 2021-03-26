package com.example.code_of_duty.currentTrip

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.code_of_duty.location.LocationViewModel
import com.example.code_of_duty.locationDatabase.SavedLocationDao
import com.example.code_of_duty.tripDatabase.TripDao

class CurrentTripViewModelFactory(
    private val dataSource: TripDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentTripViewModel::class.java)) {
            return CurrentTripViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}