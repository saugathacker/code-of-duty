package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.aimsapp.database.tripDatabase.Driver
import com.example.aimsapp.database.tripDatabase.Truck

data class TruckWithDrivers(
    @Embedded val truck: Truck,

    @Relation(
        parentColumn = "truckId",
        entityColumn = "driverCode",
        associateBy = Junction(DriverTruckCrossRef::class)
    )

    val drivers: List<Driver>
)