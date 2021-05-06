package com.example.aimsapp.database.tripDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Contains query to extract data from databse
 */
@Dao
interface TripDao {

    //inserting a single trip in the trip_table
    @Insert
    fun insertTrip(trip: Trip)

    //inserting all trips in the trip_table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllTrips(trips: List<Trip>)

    //query to get all the trips
    @Query("Select * from trip_table")
    fun getAllTrips(): LiveData<List<Trip>>

    //query to clear all trips
    @Query("Delete from trip_table")
    fun clearAllTrips()

    //inserting waypoint to the wayPoint table
    @Insert
    fun insertPoint(wayPoint: WayPoint)

    //inserting all waypoints in the wayPoints table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllPoints(wayPoints: List<WayPoint>)

    //query to delete from way_point table
    @Query("Delete from way_point_table")
    fun clearAllWayPoints()

    //query that selects all the waypoint from single trip
    @Query("Select * from way_point_table where ownerTripId = :tripId")
    fun getWayPointsByTripId(tripId: Long): LiveData<List<WayPoint>>

    //query that selects all the waypoint from single trip
    @Query("Select * from way_point_table where ownerTripId = :tripId and seqNum = :seqNum")
    suspend fun getWayPointByIds(tripId: Long, seqNum: Long): List<WayPoint>

    //function to update the trip table
    @Update
    suspend fun updateTrip(trip: Trip)

    //function to update the waypoints
    @Update
    suspend fun updatePoint(point: WayPoint)

    //function to insert the form
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForm(form: Form)

    //function to update the form
    @Update
    suspend fun updateForm(form: Form)

    //query to get form by id
    @Query("Select * from form_table where ownerSeqNum =:seqNum and ownerTripId = :tripId")
    suspend fun getFormById(seqNum: Long, tripId: Long): List<Form>
}