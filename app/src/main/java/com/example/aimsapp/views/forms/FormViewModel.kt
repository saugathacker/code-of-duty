package com.example.aimsapp.views.forms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormViewModel() : ViewModel(){

    private val _productType = MutableLiveData<String>()
    val productType: LiveData<String>
        get() = _productType

    private val _startTime = MutableLiveData<String>()
    val startTime: LiveData<String>
        get() = _startTime

    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String>
        get() = _startDate

    private val _endTime = MutableLiveData<String>()
    val endTime: LiveData<String>
        get() = _endTime

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String>
        get() = _endDate

    private val _grossGallons = MutableLiveData<Double>()
    val grossGallons: LiveData<Double>
        get() = _grossGallons

    private val _netGallons = MutableLiveData<Double>()
    val netGallons: LiveData<Double>
        get() = _netGallons

    private val _initialFuelReading = MutableLiveData<Double>()
    val initialFuelReading: LiveData<Double>
        get() = _initialFuelReading

    private val _finalFuelReading = MutableLiveData<Double>()
    val finalFuelReading: LiveData<Double>
        get() = _finalFuelReading

    private val _notes = MutableLiveData<String>()
    val notes: LiveData<String>
        get() = _notes

    init {
        _productType.value = ""
        _startTime.value = ""
        _startDate.value=""
        _endTime.value = ""
        _endDate.value=""
        _grossGallons.value = 0.0
        _netGallons.value = 0.0
        _initialFuelReading.value = 0.0
        _finalFuelReading.value = 0.0
        _notes.value = ""
    }

    fun setValues(type:String = "", start: String = "", end: String = "", gross:Double =0.0, net:Double = 0.0,initial:Double = 0.0,final:Double = 0.0, notes: String){
        _productType.value = type
        _startTime.value = start
        _startDate.value= start
        _endTime.value = end
        _endDate.value=end
        _grossGallons.value = gross
        _netGallons.value = net
        _initialFuelReading.value = initial
        _finalFuelReading.value = final
        _notes.value = notes
    }
}
