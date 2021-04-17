package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Entity

@Entity( primaryKeys = ["driverCode", "truckId"])
data class DriverTruckCrossRef(
    val driverCode: String,
    val truckId: Long
)