package com.example.aimsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.network.TripApi
import com.example.aimsapp.network.Trips
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TripRepository(private val database: TripDatabase) {

    fun getTrips():LiveData<List<Trip>> = database.dao.getAllTrips()
    fun getWaPointById():LiveData<List<WayPoint>> = database.dao.getWayPointsByTripId(159)


    suspend fun refreshTrips() {
        withContext(Dispatchers.IO) {
            val response = TripApi.retrofitService.getProperties().await()
            val tripList: ArrayList<Trip> = arrayListOf()
            val pointList: ArrayList<WayPoint> = arrayListOf()

            val waypoints: List<Trips> = response.data.resultSet1

            for (waypoint in waypoints) {
                tripList.add(waypoint.getTrip())
                pointList.add(waypoint.getWayPoint())
            }

            database.dao.insertAllTrips(tripList)
            database.dao.insertAllPoints(pointList)
        }
    }
}