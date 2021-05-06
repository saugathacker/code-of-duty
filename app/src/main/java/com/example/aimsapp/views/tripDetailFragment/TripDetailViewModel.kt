package com.example.aimsapp.views.tripDetailFragment

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDao
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.repository.TripRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class TripDetailViewModel(val trip: Trip, dataSource: TripDao, application: Application) : AndroidViewModel(application)
{

    val tripDatabase = getInstance(application)
    private val repo = TripRepository(tripDatabase)

    var wayPoints: LiveData<List<WayPoint>>

    private val _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip

    private val _driverName = MutableLiveData<String>()
    val driverName: LiveData<String>
        get() = _driverName

    private val _tripName = MutableLiveData<String>()
    val tripName: LiveData<String>
        get() = _tripName

    val sourceNum= MutableLiveData<Int>()
    val siteNum = MutableLiveData<Int>()
    val completedNum = MutableLiveData<String>()

    init
    {
        _selectedTrip.value = trip
        _driverName.value = trip.driverName
        _tripName.value = trip.tripName
        wayPoints = repo.getWaPointById(trip.tripId)
    }

    fun updateTrip(trip: Trip) = viewModelScope.launch {
        repo.updateTrip(trip)
    }

    fun updatePoint(point: WayPoint) = viewModelScope.launch {
        repo.updatePoint(point)
    }


    fun startTrip(){
        _selectedTrip.value?.started = true
        _selectedTrip.value?.let { updateTrip(it) }
        val point = getNextWayPoint()
        if (point != null) {
            point.started = true
            updatePoint(point)
        }
        val timestamp = LocalDateTime.now()
        Log.i("AIMS_Dispatcher", "Trip status sent to Dispatcher!\n\"TripID\": ${trip.tripId},\n" +
                "\"StatusCode\": \"SelTrip\",\n" +
                "\"StatusComment\": \"Select Trip\",\n" +
                "\"Incoming\": true,\n" +
                "\"StatusDate\":  \"${timestamp.toString()}\"")
    }

    fun completedTrip(){
        _selectedTrip.value?.completed = true
        _selectedTrip.value?.let { updateTrip(it) }
    }

    fun getNextWayPoint(): WayPoint? {
        for (point in wayPoints.value!!){
            if(!point.completed){
                return point
            }
        }
        completedTrip()
        return null
    }

    fun updateStats(list: List<WayPoint>) {
        var sourceCounter = 0
        var siteCounter = 0
        var completedCounter = 0
        list?.let {
            for(point in it){
                if(point.waypointTypeDescription == "Source")
                {
                    sourceCounter++
                }
                else{
                    siteCounter++
                }

                if(point.completed){
                    completedCounter++
                }
            }
        }

        sourceNum.value = sourceCounter
        siteNum.value = siteCounter
        completedNum.value = "${completedCounter.toString()}/${list.size}"
    }
}