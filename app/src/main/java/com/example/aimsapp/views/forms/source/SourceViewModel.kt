package com.example.aimsapp.views.forms.source

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.example.aimsapp.database.tripDatabase.Form
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.repository.TripRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SourceViewModel(application: Application): AndroidViewModel(application),Observable {
    val database = getInstance(application)
    val repo = TripRepository(database)

    lateinit var form: Form

    var exist = false

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


    fun startForm(wayPoint: WayPoint){
        var forms = listOf<Form>()
        viewModelScope.launch {
           forms = repo.getFormById(wayPoint.seqNum,wayPoint.ownerTripId)
            if(forms.isEmpty()){
                val newForm = Form()
                Log.i("CloseForm","No form on database")
                newForm.ownerSeqNum = wayPoint.seqNum
                newForm.ownerTripId = wayPoint.ownerTripId
                viewModelScope.launch {
                    repo.insertForm(newForm)
                }
                form = newForm
                Log.i("CloseForm","Form Inserted")
            }
            else{
                retriveData(forms[0])
                Log.i("CloseForm","form on database ${forms.get(0).productType}")
                exist = true
            }
        }
    }

    fun retriveData(get: Form) {
        form = get
        productType.value = get.productType
        startDate.value = get.startDate
        startTime.value = get.startTime
        endDate.value = get.endDate
        endTime.value = get.endTime
        grossGallons.value = if(get.grossGallons==0.0) "" else get.grossGallons.toString()
        netGallons.value = if(get.netGallons == 0.0) "" else get.netGallons.toString()
        billOfLading.value = get.billOfLading
        notes.value = get.notes

        Log.i("CloseForm","Data retrieved")

    }

    fun saveForm(){
        viewModelScope.launch {
            Log.i("CloseForm","Saved")
            form.productType = productType.value.toString()
            form.startDate = startDate.value.toString()
            form.startTime = startTime.value.toString()
            form.endDate = endDate.value.toString()
            form.endTime = endTime.value.toString()
            grossGallons.value?.let {
                form.grossGallons = if (it == "") 0.0 else it.toDouble()
            }
            netGallons.value?.let{
                form.netGallons = if(it == "") 0.0 else it.toDouble()
            }
            form.billOfLading = billOfLading.value.toString()
            form.notes = notes.value.toString()
            Log.i("CloseForm","Saved2 ${productType.value}")
            repo.updateForm(form)
        }
    }

    fun submitForm(){
        Log.i("AIMSLOG", "Saved for to the database\n" +
                " ${productType.value}\n ${startDate.value}, ${startTime.value}\n" +
                " ${endDate.value}, ${endTime.value} \n" +
                " ${grossGallons.value}, ${netGallons.value}\n" +
                " ${billOfLading.value}, ${notes.value}")
    }

    fun updatePoint(wayPoint: WayPoint) {
        viewModelScope.launch {
            repo.updatePoint(wayPoint)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("CloseForm", "ViewModel destroyed")
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry()}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }


}