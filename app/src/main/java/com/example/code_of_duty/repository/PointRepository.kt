package com.example.code_of_duty.repository

import androidx.lifecycle.LiveData
import com.example.code_of_duty.tripDatabase.Point
import com.example.code_of_duty.tripDatabase.Trip
import com.example.code_of_duty.tripDatabase.TripDatabase

class PointRepository (private val database: TripDatabase){

    fun getPointByTripId(id: Long): LiveData<List<Point>>{
        return database.tripDao.getPointByTripId(id)
    }
}