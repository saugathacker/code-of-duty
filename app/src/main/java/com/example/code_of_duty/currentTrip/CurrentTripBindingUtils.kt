package com.example.code_of_duty.currentTrip

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.code_of_duty.tripDatabase.Trip

@BindingAdapter("tripName")
fun TextView.setTripName(item: Trip?) {
    item?.let {
        val string = "Tripname: ${item.tripName} with Trip Id: ${item.tripId} "
        text = string
    }
}

@BindingAdapter("driverName")
fun TextView.setSleepQualityString(item: Trip?) {
    item?.let {
        val string = "Driver Name: ${item.driverName.trim()} with Truck Id: ${item.truckId}"
        text = string
    }
}