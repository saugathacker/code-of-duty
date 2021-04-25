package com.example.aimsapp.repository

import androidx.lifecycle.LiveData
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.network.TripApi
import com.example.aimsapp.network.Trips
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * TripRepository to get data from database
 */
class TripRepository(private val database: TripDatabase) {

    fun getTrips():LiveData<List<Trip>> = database.dao.getAllTrips()
    fun getWaPointById(tripId: Long):LiveData<List<WayPoint>> = database.dao.getWayPointsByTripId(tripId)

    suspend fun updateTrip(trip: Trip) = database.dao.updateTrip(trip)
    suspend fun updatePoint(point: WayPoint) = database.dao.updatePoint(point)

    /**
     * Refreshes trips and waypoints in app
     */
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