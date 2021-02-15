package com.example.code_of_duty.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_location_table")
data class SavedLocation(
        @PrimaryKey(autoGenerate = true)
        var locationId: Long = 0L,
        @ColumnInfo(name = "latitude")
        var latitude: Double = 0.0,
        @ColumnInfo(name = "longitude")
        var longitude: Double = 0.0,
        @ColumnInfo(name = "name_of_location")
        var locationName: String = "My Home",
        @ColumnInfo(name = "is_indoor")
        var isIndoor: Int = 0
)