package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aimsapp.database.tripDatabase.Form
import com.example.aimsapp.database.tripDatabase.WayPoint

data class WayPointAndForm (
    @Embedded val wayPoint: WayPoint,

    @Relation(
        parentColumn = "seqNum",
        entityColumn = "seqNum"
    )
    val form: Form
)