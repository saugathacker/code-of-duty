package com.example.aimsapp.database.tripDatabase

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "trip_table")
@Parcelize
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
) : Parcelable

@Entity(tableName = "way_point_table",foreignKeys = [(ForeignKey(entity = Trip::class,
    parentColumns = arrayOf("tripId"),
    childColumns = arrayOf("ownerTripId"),
    onDelete = ForeignKey.CASCADE))],indices = [
    Index("ownerTripId")])
@Parcelize
data class WayPoint(
    @PrimaryKey()
    var seqNum: Long = 0,
    var ownerTripId: Long = 0,
    var address1: String = "",
    var address2: String? = "",
    var city: String = "",
    var delReqLineNum: Long? = 0,
    var delReqNum: Long? = 0,
    var destinationCode: String = "",
    var destinationName: String = "",
    var fill: String? = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var postalCode: Int = 0,
    var productCode: String? = "",
    var productDesc: String? = "",
    var productId: Long? = 0,
    var requestedQty: Double? = 0.0,
    var siteContainerCode: String? = "",
    var siteContainerDescription: String? = "",
    var stateAbbrev: String ="",
    var uOM: String? ="",
    var waypointTypeDescription: String = ""
) : Parcelable


