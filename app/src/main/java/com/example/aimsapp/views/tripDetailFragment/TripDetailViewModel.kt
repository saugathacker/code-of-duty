package com.example.aimsapp.views.tripDetailFragment

import android.app.Application
import androidx.lifecycle.*
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDao
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.repository.TripRepository

class TripDetailViewModel(trip: Trip, dataSource: TripDao, application: Application) : AndroidViewModel(application)
{

    val tripDatabase = getInstance(application)
    val repo = TripRepository(tripDatabase)

    val wayPoints = repo.getWaPointById()

    private val _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip

    init
    {
        _selectedTrip.value = trip
    }

}