package com.example.code_of_duty.tripDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao {

    @Insert
    fun insert(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( trips: List<Trip>)

    @Query("Select * from trip_table")
    fun allTrips(): LiveData<List<Trip>>

    @Query("Delete from trip_table")
    fun clearAll()

}