package com.example.code_of_duty.database

import androidx.room.Entity

@Entity(tableName = "saved_location_table")
data class SavedLocation(
        var locationId: Long = 0L,


)