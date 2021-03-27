package com.example.code_of_duty.currentTrip

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.code_of_duty.repository.PointRepository
import com.example.code_of_duty.repository.TripRepository
import com.example.code_of_duty.tripDatabase.TripDao
import com.example.code_of_duty.tripDatabase.TripDatabase.Companion.getInstance
import kotlinx.coroutines.*

class CurrentTripViewModel(
    val database: TripDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    private val tripDatabase = getInstance(application)
    private val tripRepository = TripRepository(tripDatabase)
    private val pointRepository = PointRepository(tripDatabase)


    private var _tripName = MutableLiveData<String>()
    val tripName: LiveData<String>
        get() = _tripName

    val points = pointRepository.getPointsByTripId()

    private var _navigateToTripDetail = MutableLiveData<Long>()
    val navigateToTripDetail
        get() = _navigateToTripDetail


    init {
        coroutineScope.launch {
            tripRepository.refreshTrips()
        }

    }

    val trips = tripRepository.trips

    fun onTripClicked(id: Long?){
        _navigateToTripDetail.value = id!!
    }

    fun onTripDetailNavigated(){
        _navigateToTripDetail.value = 0
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}