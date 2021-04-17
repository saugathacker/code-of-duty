package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.aimsapp.database.tripDatabase.Driver
import com.example.aimsapp.database.tripDatabase.Truck

data class DriverWithTrucks(
    @Embedded val driver: Driver,

    @Relation(
        parentColumn = "driverCode",
        entityColumn = "truckId",
        associateBy = Junction(DriverTruckCrossRef::class)
    )

    val trucks: List<Truck>
)