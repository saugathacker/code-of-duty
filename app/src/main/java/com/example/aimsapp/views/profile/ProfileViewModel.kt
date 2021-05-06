package com.example.aimsapp.views.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.repository.TripRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {


    val database = getInstance(application)
    val repo = TripRepository(database)

    val trips = repo.getTrips()
    val driverName = MutableLiveData<String>()
    val tripCompleted = MutableLiveData<String>()
    val truckCode = MutableLiveData<String>()
    val trailerCode = MutableLiveData<String>()

    fun updateValues(trip: Trip) {
        driverName.value = trip.driverName.trim()
        truckCode.value = trip.truckDesc
        trailerCode.value = trip.trailerDesc
    }

    fun updateCompleted(num: Int){
        tripCompleted.value = num.toString()
    }

}