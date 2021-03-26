package com.example.code_of_duty.location

import android.app.Application
import androidx.lifecycle.*
import com.example.code_of_duty.locationDatabase.SavedLocation
import com.example.code_of_duty.locationDatabase.SavedLocationDao
import com.example.code_of_duty.formatLocations
import kotlinx.coroutines.*

class LocationViewModel(
    val  database: SavedLocationDao,
    application: Application
) : AndroidViewModel(application) {

    private var _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double>
        get() = _latitude

    private var _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double>
        get() = _longitude

    private var _coord = MutableLiveData<String>()
    val coord: LiveData<String>
        get() = _coord

    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private var _isIndoor = MutableLiveData<Boolean>()
    val isIndoor: LiveData<Boolean>
        get() = _isIndoor

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val locations = database.allLocation()



    init {
        _latitude.value = 10.0
        _longitude.value = 10.0
        _coord.value = "Your location is: ${_latitude.value.toString()}, ${_longitude.value.toString()}"
    }

    val locationString = Transformations.map(locations) {nights ->
        formatLocations(nights)
    }



    fun setCoord(lat:Double, long:Double ){
        _latitude.value = lat
        _longitude.value = long
        _coord.value = "Your location is: ${_latitude.value.toString()}, ${_longitude.value.toString()}"
    }

    fun setCoordText(text: String){
        _coord.value = text
    }


    fun clearCoord() {
        _latitude.value = 0.0
        _longitude.value = 0.0
        _coord.value = "Your location is: ${_latitude.value.toString()}, ${_longitude.value.toString()}"
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onSaveLocation(name: String, isIndoor:Int){
        uiScope.launch {
            val newLocation = SavedLocation()
            newLocation.locationName = name
            newLocation.latitude = _latitude.value!!
            newLocation.longitude  = _longitude.value!!
            newLocation.isIndoor = isIndoor
            insert(newLocation)
        }
    }

    private suspend fun insert(location: SavedLocation){
        withContext(Dispatchers.IO){
            database.insert(location)
        }
    }

    fun onClear(){
        uiScope.launch{
            clearAll()
        }
    }

    private suspend fun clearAll(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }






}