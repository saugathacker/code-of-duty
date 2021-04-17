package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.WayPoint

data class TripWithWayPoints(
    @Embedded val trip: Trip,

    @Relation(
        parentColumn = "tripId",
        entityColumn = "tripId"
    )
    val wayPoints: List<WayPoint>
)