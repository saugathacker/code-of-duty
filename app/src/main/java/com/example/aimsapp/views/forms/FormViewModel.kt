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

    private val _startTimeAndDate = MutableLiveData<String>()
    val startTimeAndDate: LiveData<String>
        get() = _startTimeAndDate

    private val _endTimeAndDate = MutableLiveData<String>()
    val endTimeAndDate: LiveData<String>
        get() = _endTimeAndDate

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
        _startTimeAndDate.value = ""
        _endTimeAndDate.value = ""
        _grossGallons.value = 0.0
        _netGallons.value = 0.0
        _initialFuelReading.value = 0.0
        _finalFuelReading.value = 0.0
        _notes.value = ""
    }
}
