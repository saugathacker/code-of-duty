package com.example.aimsapp.views.currentTrip

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.database.tripDatabase.TripDao

/**
 * Constructor of View Model
 */
class CurrentTripViewModelFactory(private val dataSource: TripDao, private val application: Application ):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentTripViewModel::class.java)){
            return CurrentTripViewModel(dataSource,application) as T
        }

        //throws IllegalArgumentException
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}