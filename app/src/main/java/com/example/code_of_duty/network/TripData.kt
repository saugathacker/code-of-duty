package com.example.code_of_duty.network

import com.example.code_of_duty.tripDatabase.Trip
import com.squareup.moshi.Json

data class Response(
    val data: TripDate
)

data class TripDate(
    val resultSet2: List<StatusSet>,
    val resultSet1: List<Waypoint>
)

data class StatusSet(
    @Json(name = "StatusCode")
    val statusCode: Long,
    @Json(name = "Status")
    val status: String
)


data class Waypoint(
    @Json(name = "DriverCode")
    val driverCode: String,
    @Json(name = "DriverName")
    val driverName: String,
    @Json(name = "TruckId")
    val truckId: Long,
    @Json(name = "TruckCode")
    val truckCode: String,
    @Json(name = "TruckDesc")
    val truckDesc: String,
    @Json(name = "TrailerId")
    val trailerId: Long,
    @Json(name = "TrailerCode")
    val trailerCode: String,
    @Json(name = "TrailerDesc")
    val trailerDesc: String,
    @Json(name = "TripId")
    val tripId: Long,
    @Json(name = "TripName")
    val tripName: String,
    @Json(name = "TripDate")
    val tripDate: String,
    @Json(name = "SeqNum")
    val seqNum: Long,
    @Json(name = "WaypointTypeDescription")
    val waypointTypeDescription: String,
    @Json(name = "Latitude")
    val latitude: Double,
    @Json(name = "Longitude")
    val longitude: Double,
    @Json(name = "DestinationCode")
    val destinationCode: String,
    @Json(name = "DestinationName")
    val destinationName: String,
    @Json(name = "SiteContainerCode")
    val siteContainerCode: String?,
    @Json(name = "SiteContainerDescription")
    val siteContainerDescription: String?,
    @Json(name = "Address1")
    val address1: String,
    @Json(name = "Address2")
    val address2: String?,
    @Json(name = "City")
    val city: String,
    @Json(name = "StateAbbrev")
    val stateAbbrev: String,
    @Json(name = "PostalCode")
    val postalCode: Int,
    @Json(name = "DelReqNum")
    val delReqNum: Long?,
    @Json(name = "DelReqLineNum")
    val delReqLineNum: Long?,
    @Json(name = "ProductId")
    val productId: Long?,
    @Json(name = "ProductCode")
    val productCode: String?,
    @Json(name = "ProductDesc")
    val productDesc: String?,
    @Json(name = "RequestedQty")
    val requestedQty: Double?,
    @Json(name = "UOM")
    val uOM: String?,
    @Json(name = "Fill")
    val fill: String
){
    fun getTrip(): Trip{
        val newTrip = Trip()
        newTrip.tripId = tripId
        newTrip.tripName = tripName
        newTrip.driverCode = driverCode
        newTrip.driverName = driverName
        newTrip.truckCode = truckCode
        newTrip.truckDesc = truckDesc
        newTrip.truckId = truckId
        return  newTrip
    }
}

