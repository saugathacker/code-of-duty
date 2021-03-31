package com.example.aimsapp.views.currentTrip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDao
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.repository.TripRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CurrentTripViewModel(
    dataSource: TripDao,
    application: Application
) : AndroidViewModel(application) {

    private lateinit var tripDatabase: TripDatabase
    private lateinit var repo: TripRepository
    private var viewModelJob = Job()



    init{
        viewModelScope.launch{
            tripDatabase = getInstance(application)
            repo = TripRepository(tripDatabase)
            repo.refreshTrips()

        }
    }

    val trips = repo.getTrips()
    val points = repo.getWaPointById()


}