package com.example.aimsapp.views.currentTrip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimsapp.database.tripDatabase.TripDao
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.repository.TripRepository
import kotlinx.coroutines.launch

/**
 * This is the view model of the current trip
 */
class CurrentTripViewModel(dataSource: TripDao, application: Application) :
    AndroidViewModel(application) {
    //creating instance for tripDatabase and repo
    private var tripDatabase: TripDatabase = getInstance(application)
    private var repo: TripRepository

    //initialize the view model
    init {
        repo = TripRepository(tripDatabase)
    }

    //refresh the trips
    fun refresh() {
        viewModelScope.launch {
            repo.refreshTrips()
        }
    }

    //gets trip from the repository
    val trips = repo.getTrips()


}