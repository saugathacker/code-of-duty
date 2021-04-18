package com.example.aimsapp.views.currentTrip

import android.app.Application
import androidx.lifecycle.*
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

class CurrentTripViewModel(dataSource: TripDao, application: Application) : AndroidViewModel(application)
{

    private var tripDatabase: TripDatabase = getInstance(application)
    private var repo: TripRepository
    private var viewModelJob = Job()
    private val _showWelcomeText = MutableLiveData<Boolean>()
    val showWelcomeText: LiveData<Boolean>
        get() = _showWelcomeText

    init{
        repo = TripRepository(tripDatabase)
    }

    fun refresh(){
        viewModelScope.launch{
            repo.refreshTrips()
            _showWelcomeText.value = false
        }
    }

    val trips = repo.getTrips()
    val points = repo.getWaPointById()


}