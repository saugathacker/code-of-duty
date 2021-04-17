package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.aimsapp.database.tripDatabase.Trailer
import com.example.aimsapp.database.tripDatabase.Truck

data class TrailerWithTrucks(
    @Embedded val trailer: Truck,

    @Relation(
        parentColumn = "trailerId",
        entityColumn = "truckId",
        associateBy = Junction(TruckTrailerCrossRef::class)
    )
    val trucks: List<Truck>
)