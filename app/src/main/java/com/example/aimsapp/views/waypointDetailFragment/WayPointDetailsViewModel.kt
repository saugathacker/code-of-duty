package com.example.aimsapp.views.waypointDetailFragment

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

    private val _address2 = MutableLiveData<String>()
    val address2: LiveData<String>
        get() = _address2


    init
    {
        _selectedWayPoint.value = wayPoint
        _selectedWayPoint.value?.let{
            _address2.value = "${it.city.trim()}, ${it.stateAbbrev.trim()} ${it.postalCode}"
        }

    }
}