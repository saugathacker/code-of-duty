package com.example.aimsapp.views.tripDetailFragment

import android.app.Application
import androidx.lifecycle.*
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDao
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.repository.TripRepository
import kotlinx.coroutines.launch

class TripDetailViewModel(trip: Trip, dataSource: TripDao, application: Application) : AndroidViewModel(application)
{

    val tripDatabase = getInstance(application)
    val repo = TripRepository(tripDatabase)

    val wayPoints = repo.getWaPointById(trip.tripId)

    private val _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip

    init
    {
        _selectedTrip.value = trip
    }

    fun updateTrip(trip: Trip) = viewModelScope.launch {
        repo.updateTrip(trip)
    }

    fun updatePoint(point: WayPoint) = viewModelScope.launch {
        repo.updatePoint(point)
    }

    fun startTrip(){
        _selectedTrip.value?.started = true
        _selectedTrip.value?.let { updateTrip(it) }
    }

    fun completedTrip(){
        _selectedTrip.value?.completed = true
        _selectedTrip.value?.let { updateTrip(it) }
    }
}