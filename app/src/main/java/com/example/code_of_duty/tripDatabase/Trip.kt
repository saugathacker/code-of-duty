package com.example.code_of_duty.tripDatabase

import androidx.room.*

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

@Entity(tableName = "point_table",foreignKeys = [(ForeignKey(entity = Trip::class,
    parentColumns = arrayOf("tripId"),
    childColumns = arrayOf("tripId"),
    onDelete = ForeignKey.CASCADE))],indices = [
    Index("tripId")

])
data class Point(
    val tripId: Long = 0L,
    val address1: String,
    val address2: String?,
    val city: String,
    val delReqLineNum: Long?,
    val delReqNum: Long?,
    val destinationCode: String,
    val destinationName: String,
    val fill: String,
    val latitude: Double,
    val longitude: Double,
    val postalCode: Int,
    val productCode: String?,
    val productDesc: String?,
    val productId: Long?,
    val requestedQty: Double?,
    @PrimaryKey
    val seqNum: Long,
    val siteContainerCode: String?,
    val siteContainerDescription: String?,
    val stateAbbrev: String,
    val uOM: String?,
    val waypointTypeDescription: String
)

//fun List<Point>.asDomainModel(): List<Point> {
//    return map {
//        Point(
//            tripId = it.tripId,
//            address1 = it.address1,
//            address2 = it.address2,
//            city = it.city,
//            delReqLineNum = it.delReqLineNum,
//            delReqNum = it.delReqNum,
//            destinationCode = it.destinationCode,
//            destinationName = it.destinationName,
//            fill = it.fill,
//            latitude = it.latitude,
//            longitude = it.longitude,
//            postalCode = it.postalCode,
//            productCode = it.productCode,
//            productDesc = it.productDesc,
//            productId = it.productId,
//            requestedQty = it.requestedQty,
//            seqNum = it.seqNum,
//            siteContainerCode = it.siteContainerCode,
//            siteContainerDescription = it.siteContainerDescription,
//            stateAbbrev = it.stateAbbrev,
//            uOM = it.uOM,
//            waypointTypeDescription = it.waypointTypeDescription
//        )
//    }
//}