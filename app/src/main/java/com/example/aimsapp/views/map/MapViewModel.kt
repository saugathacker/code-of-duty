package com.example.aimsapp.views.map

import android.app.Application
import androidx.lifecycle.*
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.repository.TripRepository
import com.here.android.mpa.routing.Route
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {


    val database = getInstance(application)
    val repo = TripRepository(database)
    var route: Route? = null
    var inNavigationMode: Boolean
    lateinit var wayPoint: WayPoint

    init {
        inNavigationMode = false
    }

    fun getPoint(tripId: Long, seqNum: Long){
        viewModelScope.launch {
           wayPoint = repo.getWayPointByIds(tripId,seqNum)[0]
        }
    }

    fun pointArrived(){
        wayPoint.arrived = true
        updatePoint(wayPoint)
    }

    fun updatePoint(point: WayPoint){
        viewModelScope.launch {
            repo.updatePoint(point)
        }
    }

    fun navigationStarted(){
        inNavigationMode = true
    }

    fun navigationEnded(){
        inNavigationMode = false
    }

}