package com.example.aimsapp.database.tripDatabase

import androidx.room.*

@Entity(tableName = "trip_table")
data class Trip(
    @PrimaryKey
    var tripId:Long = 0L,
    var tripName: String = "",
    var driverCode: String = "",
    var driverName: String = "",
    var truckId: Long = 0L,
    var truckCode: String = "",
    var truckDesc: String = "",
    var trailerId:Long = 0L,
    var trailerCode:String = "",
    var trailerDesc:String = "",
    var tripDate:String = ""
)

@Entity(tableName = "way_point_table",foreignKeys = [(ForeignKey(entity = Trip::class,
    parentColumns = arrayOf("tripId"),
    childColumns = arrayOf("ownerTripId"),
    onDelete = ForeignKey.CASCADE))],indices = [
    Index("ownerTripId")])
data class WayPoint(
    @PrimaryKey()
    val seqNum: Long = 0,
    val ownerTripId: Long = 0,
    val address1: String = "",
    val address2: String = "",
    val city: String = "",
    val delReqLineNum: Long = 0,
    val delReqNum: Long = 0,
    val destinationCode: String = "",
    val destinationName: String = "",
    val fill: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val postalCode: Int = 0,
    val productCode: String = "",
    val productDesc: String = "",
    val productId: Long = 0,
    val requestedQty: Double = 0.0,
    val siteContainerCode: String = "",
    val siteContainerDescription: String = "",
    val stateAbbrev: String ="",
    val uOM: String ="",
    val waypointTypeDescription: String = ""
)



