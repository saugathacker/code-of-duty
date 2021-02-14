package com.example.code_of_duty.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private var _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double>
        get() = _latitude

    private var _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double>
        get() = _longitude

    private var _coord = MutableLiveData<String>()
    val coord: LiveData<String>
        get() = _coord

    init {
        _latitude.value = 10.0
        _longitude.value = 10.0
        _coord.value = "Your location is: ${_latitude.value.toString()}, ${_longitude.value.toString()}"
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


}