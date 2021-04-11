package com.example.aimsapp.views.tripDetailFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDao
import java.lang.IllegalArgumentException

class TripDetailViewModelFactory(private val trip: Trip, private val dataSource: TripDao, private val application: Application):ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(TripDetailViewModel::class.java))
        {
            return TripDetailViewModel(trip, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}