package com.example.code_of_duty.locationDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedLocationDao{

    @Insert
    fun insert(location: SavedLocation)

    @Query("SELECT * from saved_location_table ORDER BY locationId DESC")
    fun allLocation(): LiveData<List<SavedLocation>>

    @Query("DELETE from saved_location_table")
    fun clear()
}