package com.example.aimsapp.views.map

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.repository.TripRepository
import com.here.android.mpa.routing.Route
import kotlinx.coroutines.launch
import java.time.LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
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
           val points = repo.getWayPointByIds(tripId,seqNum)
            if (points.isNotEmpty()){
                wayPoint = points[0]
            }
        }
    }


    fun pointArrived(){
        wayPoint.arrived = true
        updatePoint(wayPoint)
        val timestamp = LocalDateTime.now()
        val statusCode = if(wayPoint.waypointTypeDescription.equals("Source")) "ArriveSrc" else "ArriveSite"
        val statusComment = if(wayPoint.waypointTypeDescription.equals("Source")) "Arrive at Source" else "Arrive at Site"
        Log.i("AIMS_Dispatcher", "Trip status sent to Dispatcher!\n\"TripID\": ${wayPoint.ownerTripId},\n" +
                "\"StatusCode\": \"ArriveSrc\",\n" +
                "\"StatusComment\": \"Arrive At Source\",\n" +
                "\"Incoming\": true,\n" +
                "\"StatusDate\":  \"${timestamp.toString()}\"")
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