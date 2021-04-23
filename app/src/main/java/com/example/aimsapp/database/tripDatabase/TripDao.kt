package com.example.aimsapp.database.tripDatabase

import android.graphics.Point
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao{

    @Insert
    fun insertTrip(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrips(trips: List<Trip>)

    @Query("Select * from trip_table")
    fun getAllTrips(): LiveData<List<Trip>>

    @Query("Delete from trip_table")
    fun clearAllTrips()

    @Insert
    fun insertPoint(wayPoint: WayPoint)

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    fun insertAllPoints(wayPoints: List<WayPoint>)

    @Query("Delete from way_point_table")
    fun clearAllWayPoints()

    @Query("Select * from way_point_table where ownerTripId = :tripId")
    fun getWayPointsByTripId(tripId: Long): LiveData<List<WayPoint>>

    @Update
    suspend fun updateTrip(trip: Trip)

    @Update
    suspend fun updatePoint(point: WayPoint)
}