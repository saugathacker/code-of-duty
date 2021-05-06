package com.example.aimsapp.network

import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.squareup.moshi.Json

data class Response(
    val data: TripDate
)

data class TripDate(
    val resultSet2: List<StatusSet>,
    val resultSet1: List<Trips>
)

data class StatusSet(
    @Json(name = "StatusCode")
    val statusCode: Long,
    @Json(name = "Status")
    val status: String
)


data class Trips(
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
    val fill: String,
    @Json(name = "SourceID")
    val sourceId: Long?,
    @Json(name = "SiteID")
    val siteId: Long?
) {
    fun getTrip(): Trip {
        val newTrip = Trip()
        newTrip.tripId = tripId
        newTrip.tripName = tripName
        newTrip.driverCode = driverCode
        newTrip.driverName = driverName
        newTrip.truckCode = truckCode
        newTrip.truckDesc = truckDesc
        newTrip.truckId = truckId
        newTrip.trailerCode = trailerCode
        newTrip.trailerDesc = trailerDesc
        newTrip.tripDate = tripDate
        newTrip.completed = false
        newTrip.started = false
        return newTrip
    }

    fun getWayPoint(): WayPoint {
        val newWayPoint = WayPoint()
        newWayPoint.ownerTripId = tripId
        newWayPoint.address1 = address1
        newWayPoint.address2 = address2
        newWayPoint.city = city
        newWayPoint.delReqLineNum = delReqLineNum
        newWayPoint.delReqNum = delReqNum
        newWayPoint.destinationCode = destinationCode
        newWayPoint.destinationName = destinationName
        newWayPoint.fill = fill
        newWayPoint.latitude = latitude
        newWayPoint.longitude = longitude
        newWayPoint.postalCode = postalCode
        newWayPoint.productCode = productCode
        newWayPoint.productDesc = productDesc
        newWayPoint.productId = productId
        newWayPoint.requestedQty = requestedQty
        newWayPoint.seqNum = seqNum
        newWayPoint.siteContainerCode = siteContainerCode
        newWayPoint.siteContainerDescription = siteContainerDescription
        newWayPoint.stateAbbrev = stateAbbrev
        newWayPoint.uOM = uOM
        newWayPoint.waypointTypeDescription = waypointTypeDescription
        newWayPoint.sourceId = sourceId
        newWayPoint.siteId = siteId
        newWayPoint.completed = false
        newWayPoint.started = false
        return newWayPoint
    }
}
