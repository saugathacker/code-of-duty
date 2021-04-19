package com.example.aimsapp.views.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.here.android.mpa.routing.Route

class MapViewModel() : ViewModel() {


    var route: Route? = null
    var inNavigationMode: Boolean

    init {
        inNavigationMode = false
    }


    fun navigationStarted(){
        inNavigationMode = true
    }

    fun navigationEnded(){
        inNavigationMode = false
    }

}