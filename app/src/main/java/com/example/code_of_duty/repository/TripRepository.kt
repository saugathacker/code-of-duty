package com.example.code_of_duty.repository

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.code_of_duty.network.TripApi
import com.example.code_of_duty.network.Waypoint
import com.example.code_of_duty.tripDatabase.Trip
import com.example.code_of_duty.tripDatabase.TripDatabase
import com.example.code_of_duty.tripDatabase.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TripRepository (private val database: TripDatabase){
    val trips: LiveData<List<Trip>> = Transformations.map(database.tripDao.allTrips()) {
        it.asDomainModel()
    }

    suspend fun refreshTrips() {
        withContext(Dispatchers.IO) {
            val response = TripApi.retrofitService.getProperties().await()
            val tripList: ArrayList<Trip> = arrayListOf()

            val waypoints: List<Waypoint> = response.data.resultSet1

            for (waypoint in waypoints){
                tripList.add(waypoint.getTrip())
            }

           database.tripDao.insertAll(tripList)
        }
    }
}
