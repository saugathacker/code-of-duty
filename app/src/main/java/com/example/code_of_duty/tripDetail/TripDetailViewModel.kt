package com.example.code_of_duty.tripDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.code_of_duty.repository.PointRepository
import com.example.code_of_duty.repository.TripRepository
import com.example.code_of_duty.tripDatabase.TripDao
import com.example.code_of_duty.tripDatabase.TripDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TripDetailViewModel(
    dataSource: TripDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    private val tripDatabase = TripDatabase.getInstance(application)
    private val pointRepository = PointRepository(tripDatabase)



    val points = pointRepository.getPointsByTripId()


    init {

    }




    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}