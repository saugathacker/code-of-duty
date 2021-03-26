package com.example.code_of_duty.tripDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_table")
data class Trip(
    @PrimaryKey
    var tripId:Long = 0L,
    @ColumnInfo(name = "trip_name")
    var tripName: String = "",
    @ColumnInfo(name = "driver_code")
    var driverCode: String = "",
    @ColumnInfo(name = "driver_name")
    var driverName: String = "",
    @ColumnInfo(name = "truck_id")
    var truckId: Long = 0L,
    @ColumnInfo(name = "truck_code")
    var truckCode: String = "",
    @ColumnInfo(name = "truck_desc")
    var truckDesc: String = ""
)

fun List<Trip>.asDomainModel(): List<Trip> {
    return map {
        Trip(
            tripId = it.tripId,
            tripName = it.tripName,
            driverCode = it.driverCode,
            driverName = it.driverName,
            truckId = it.truckId,
            truckCode = it.truckCode,
            truckDesc = it.truckDesc
            )
    }
}