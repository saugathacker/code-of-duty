package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.aimsapp.database.tripDatabase.Trailer
import com.example.aimsapp.database.tripDatabase.Truck

data class TruckWithTrailers(
    @Embedded val truck: Truck,

    @Relation(
        parentColumn = "truckId",
        entityColumn = "trailerId",
        associateBy = Junction(TruckTrailerCrossRef::class)
    )
    val trailers: List<Trailer>
)