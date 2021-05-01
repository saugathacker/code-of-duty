package com.example.aimsapp.database.tripDatabase

import android.graphics.Point
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao{

    @Insert
    fun insertTrip(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
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

    @Query("Select * from way_point_table where ownerTripId = :tripId and seqNum = :seqNum")
    suspend fun getWayPointByIds(tripId: Long, seqNum: Long): List<WayPoint>

    @Update
    suspend fun updateTrip(trip: Trip)

    @Update
    suspend fun updatePoint(point: WayPoint)

    @Insert
    suspend fun insertForm(form: Form)

    @Update
    suspend fun updateForm(form: Form)

    @Query("Select * from form_table where ownerSeqNum =:seqNum and ownerTripId = :tripId")
    suspend fun getFormById(seqNum: Long, tripId: Long): List<Form>
}