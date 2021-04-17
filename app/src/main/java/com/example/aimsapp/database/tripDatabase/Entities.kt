package com.example.aimsapp.database.tripDatabase

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.sql.Time

@Entity(tableName = "trip_table")
@Parcelize
data class Trip(
    @PrimaryKey
    var tripId:Long = 0L,
    var tripName: String = "",
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
    var tripId:Long = 0L,
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


@Entity(tableName = "form_table", foreignKeys = [(ForeignKey(entity = WayPoint::class,
    parentColumns = [ "seqNum"],
    childColumns = [ "ownerSeqNum"],
    onDelete = ForeignKey.CASCADE))],indices = [
    Index("ownerSeqNum")])
data class Form(
    @PrimaryKey(autoGenerate = true)
    var formId: Long = 0,
    var seqNum: Long = 0,
    var productType: String = "",
    var startDateTime: String = "",
    var endDateTime: String = "",
    var grossGallons: Double = 0.0,
    var netGallons: Double = 0.0,
    var initialFuelReading: Double = 0.0,
    var finalFuelReading: Double = 0.0,
    var notes: String = "",
    var ownerSeqNum: Long = 0
)

@Entity(tableName = "driver_table")
data class Driver(
    @PrimaryKey
    var driverCode: String = "",
    var driverName: String = ""
)

@Entity(tableName = "account_table")
data class Account(
    @PrimaryKey
    var accountId: Long = 0,
    var driverCode: String = ""
)

@Entity(tableName = "truck_table")
data class Truck(
    @PrimaryKey
    var truckId: Long = 0L,
    var truckCode: String = "",
    var truckDesc: String = ""

)

@Entity(tableName = "trailer_table")
data class Trailer(
    @PrimaryKey
    var trailerId:Long = 0L,
    var trailerCode:String = "",
    var trailerDesc:String = ""
)