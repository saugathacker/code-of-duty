package com.example.aimsapp.views.tripDetailFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.database.tripDatabase.TripDatabase.Companion.getInstance
import com.example.aimsapp.repository.TripRepository

class TripDetailViewModel(application: Application) : AndroidViewModel(application) {

    val tripDatabase = getInstance(application)
    val repo = TripRepository(tripDatabase)

    val wayPoints = repo.getWaPointById()
}