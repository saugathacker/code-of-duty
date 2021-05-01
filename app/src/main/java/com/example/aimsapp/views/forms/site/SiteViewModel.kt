package com.example.aimsapp.views.forms.site

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SiteViewModel(application: Application): AndroidViewModel(application), Observable {

    @Bindable
    val productType = MutableLiveData<String>()
    @Bindable
    val startTime = MutableLiveData<String>()
    @Bindable
    val startDate = MutableLiveData<String>()
    @Bindable
    val endTime = MutableLiveData<String>()
    @Bindable
    val endDate = MutableLiveData<String>()
    @Bindable
    val grossGallons = MutableLiveData<String>()
    @Bindable
    val netGallons = MutableLiveData<String>()
    @Bindable
    val billOfLading = MutableLiveData<String>()
    @Bindable
    val notes = MutableLiveData<String>()


    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}