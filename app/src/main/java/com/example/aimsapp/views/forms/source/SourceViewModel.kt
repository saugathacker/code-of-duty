package com.example.aimsapp.views.forms.source

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.LocalDateTime

/**
 * ViewModel for source forms
 */
class SourceViewModel(application: Application, wayPoint: WayPoint): AndroidViewModel(application),Observable {
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

    init {
        val newForm = Form()
        newForm.ownerSeqNum = wayPoint.seqNum
        newForm.ownerTripId = wayPoint.ownerTripId
        viewModelScope.launch {
            repo.insertForm(newForm)
            Log.i("CloseForm","Form Inserted")
        }
    }

    /**
     * function startform
     */
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

    /**
     * retrieves data from database
     */
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

    /**
     * function to save form
     */
    fun saveForm(){

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
        viewModelScope.launch {
            repo.insertForm(form)
        }
    }

    /**
     * updates the point and insertes into database
     */
    fun updatePoint(wayPoint: WayPoint) {
        Log.i("AIMS_Dispatcher", "Product picked up information sent to Dispatcher!\n" +
                "\"DriverCode\": \"CodeOfDuty\",\n" +
                "\"TripId\": ${wayPoint.ownerTripId},\n" +
                "\"SourceId\": ${wayPoint.sourceId},\n" +
                "\"ProductId\": ${wayPoint.productId},\n" +
                "\"BOLNum\": \"${form.billOfLading}\",\n" +
                "\"StartTime\":  \"${form.startTime}\",\n" +
                "\"EndTime\":  \"${form.endTime}\",\n" +
                "\"GrossQty\":  ${form.grossGallons},\n" +
                "\"NetQty\":  ${form.netGallons}")
        viewModelScope.launch {
            repo.updatePoint(wayPoint)
        }
    }

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("CloseForm", "ViewModel destroyed")
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry()}

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }


}