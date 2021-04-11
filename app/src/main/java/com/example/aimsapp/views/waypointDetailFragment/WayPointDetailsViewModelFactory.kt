package com.example.aimsapp.views.waypointDetailFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.database.tripDatabase.TripDao
import com.example.aimsapp.database.tripDatabase.WayPoint
import java.lang.IllegalArgumentException

class WayPointDetailsViewModelFactory(private val wayPoint: WayPoint, private val dataSource: TripDao, private val application: Application): ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(WayPointDetailsViewModel::class.java))
        {
            return WayPointDetailsViewModel(
                wayPoint,
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}