package com.example.aimsapp.views.forms.site

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aimsapp.database.tripDatabase.Form
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.repository.TripRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for site forms
 */
class SiteViewModel(application: Application, wayPoint: WayPoint) : AndroidViewModel(application),
    Observable {

    val database = getInstance(application)
    val repo = TripRepository(database)
    var exist = false

    lateinit var form: Form

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

    @Bindable
    val initialMeterReading = MutableLiveData<String>()

    @Bindable
    val initialTrailerReading = MutableLiveData<String>()

    @Bindable
    val finalMeterReading = MutableLiveData<String>()

    @Bindable
    val finalTrailerReading = MutableLiveData<String>()

    init {
        val newForm = Form()
        newForm.ownerSeqNum = wayPoint.seqNum
        newForm.ownerTripId = wayPoint.ownerTripId
        viewModelScope.launch {
            repo.insertForm(newForm)
        }
    }

    /**
     * function startForm
     */
    fun startForm(wayPoint: WayPoint) {
        var forms = listOf<Form>()
        viewModelScope.launch {
            forms = repo.getFormById(wayPoint.seqNum, wayPoint.ownerTripId)
            if (forms.isEmpty()) {
                val newForm = Form()
                newForm.ownerSeqNum = wayPoint.seqNum
                newForm.ownerTripId = wayPoint.ownerTripId
                viewModelScope.launch {
                    repo.insertForm(newForm)
                }
                form = newForm
            } else {
                retriveData(forms[0])
                exist = true
            }
        }
    }

    /**
     * gets data from the database
     */
    fun retriveData(get: Form) {
        form = get
        productType.value = get.productType
        startDate.value = get.startDate
        startTime.value = get.startTime
        endDate.value = get.endDate
        endTime.value = get.endTime
        grossGallons.value = if (get.grossGallons == 0.0) "" else get.grossGallons.toString()
        netGallons.value = if (get.netGallons == 0.0) "" else get.netGallons.toString()
        billOfLading.value = get.billOfLading
        initialMeterReading.value =
            if (get.initialMeterReading == 0.0) "" else get.initialMeterReading.toString()
        initialTrailerReading.value =
            if (get.initialTrailerReading == 0.0) "" else get.initialTrailerReading.toString()
        finalMeterReading.value =
            if (get.finalMeterReading == 0.0) "" else get.finalMeterReading.toString()
        finalTrailerReading.value =
            if (get.finalTrailerReading == 0.0) "" else get.finalTrailerReading.toString()
        notes.value = get.notes
    }

    /**
     * saves the form in database
     */
    fun saveForm() {
        viewModelScope.launch {
            form.productType = productType.value.toString()
            form.startDate = startDate.value.toString()
            form.startTime = startTime.value.toString()
            form.endDate = endDate.value.toString()
            form.endTime = endTime.value.toString()
            grossGallons.value?.let {
                form.grossGallons = if (it == "") 0.0 else it.toDouble()
            }
            netGallons.value?.let {
                form.netGallons = if (it == "") 0.0 else it.toDouble()
            }
            form.billOfLading = billOfLading.value.toString()
            form.notes = notes.value.toString()
            initialTrailerReading.value?.let {
                form.initialTrailerReading = if (it == "") 0.0 else it.toDouble()
            }
            initialMeterReading.value?.let {
                form.initialMeterReading = if (it == "") 0.0 else it.toDouble()
            }
            finalTrailerReading.value?.let {
                form.finalTrailerReading = if (it == "") 0.0 else it.toDouble()
            }
            finalMeterReading.value?.let {
                form.finalMeterReading = if (it == "") 0.0 else it.toDouble()
            }
            repo.updateForm(form)
        }
    }


    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

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

    /**
     * updates the point and inserts into database
     */
    fun updatePoint(wayPoint: WayPoint) {
        Log.i(
            "AIMS_Dispatcher", "Product picked up information sent to Dispatcher!\n" +
                    "\"DriverCode\": \"CodeOfDuty\",\n" +
                    "\"TripId\": ${wayPoint.ownerTripId},\n" +
                    "\"SourceId\": ${wayPoint.sourceId},\n" +
                    "\"ProductId\": ${wayPoint.productId},\n" +
                    "\"BOLNum\": \"${form.billOfLading}\",\n" +
                    "\"StartTime\":  \"${form.startTime}\",\n" +
                    "\"EndTime\":  \"${form.endTime}\",\n" +
                    "\"GrossQty\":  ${form.grossGallons},\n" +
                    "\"NetQty\":  ${form.netGallons},\n" +
                    "\"InitMtrRead\":  ${form.initialMeterReading},\n" +
                    "\"FinalMtrRead\": ${form.finalMeterReading}"
        )
        viewModelScope.launch {
            repo.updatePoint(wayPoint)
        }
    }
}