package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Entity

@Entity( primaryKeys = ["truckId", "trailerId"])
data class TruckTrailerCrossRef(
    val truckId: Long,
    val trailerId: Long
)