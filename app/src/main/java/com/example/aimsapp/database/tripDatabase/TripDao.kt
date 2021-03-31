package com.example.aimsapp.database.tripDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao{

    @Insert
    fun insertTrip(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrips(trips: List<Trip>)

    @Query("Select * from trip_table")
    fun getAllTrips(): List<Trip>

    @Query("Delete from trip_table")
    fun clearAllTrips()

    @Insert
    fun insertPoint(wayPoint: WayPoint)

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    fun insertAllPoints(wayPoints: List<WayPoint>)

    @Query("Delete from way_point_table")
    fun clearAllWayPoints()

    @Query("Select * from way_point_table where ownerTripId = :tripId")
    fun getWayPointsByTripId(tripId: Long)

}