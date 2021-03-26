package com.example.code_of_duty.currentTrip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.code_of_duty.formatLocations
import com.example.code_of_duty.locationDatabase.SavedLocation
import com.example.code_of_duty.network.TripApi
import com.example.code_of_duty.tripDatabase.TripDao
import kotlinx.coroutines.*
import java.lang.Exception

class CurrentTripViewModel(
    val  database: TripDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )


    private val trips = database.allTrips()

    private var _tripName = MutableLiveData<String>()
    val tripName: LiveData<String>
        get() = _tripName


    init {
        _tripName.value = "Hello"
        getTripResponse()
    }

    private fun getTripResponse() {

        coroutineScope.launch {
            val getPropertiesDeferred = TripApi.retrofitService.getProperties()
            try {
                var listResult = getPropertiesDeferred.await()

                _tripName.value = listResult.toString()
            }
            catch(e: Exception){
                _tripName.value = "Failure" + e.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}