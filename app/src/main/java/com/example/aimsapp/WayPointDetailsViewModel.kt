package com.example.aimsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aimsapp.database.tripDatabase.TripDao
import com.example.aimsapp.database.tripDatabase.WayPoint

class WayPointDetailsViewModel(wayPoint: WayPoint, dataSource: TripDao, application: Application): AndroidViewModel(application)
{
    private val _selectedWayPoint = MutableLiveData<WayPoint>()
    val selectedWayPoint: LiveData<WayPoint>
        get() = _selectedWayPoint

    init
    {
        _selectedWayPoint.value = wayPoint
    }
}