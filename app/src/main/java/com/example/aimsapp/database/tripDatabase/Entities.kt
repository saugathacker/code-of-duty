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
    var driverCode: String = "",
    var driverName: String = "",
    var truckId: Long = 0L,
    var truckCode: String = "",
    var truckDesc: String = "",
    var trailerId:Long = 0L,
    var trailerCode:String = "",
    var trailerDesc:String = "",
    var tripDate:String = "",
    var completed: Boolean = false,
    var started: Boolean = false
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
    var waypointTypeDescription: String = "",
    var completed: Boolean = false,
    var started: Boolean = false,
    var arrived: Boolean = false,
    var sourceId: Long? = 0,
    var siteId: Long? = 0
) : Parcelable {

}


@Entity(tableName = "form_table", foreignKeys = [(ForeignKey(entity = WayPoint::class,
    parentColumns = [ "seqNum"],
    childColumns = [ "ownerSeqNum"],
    onDelete = ForeignKey.CASCADE)),
    (ForeignKey(entity = Trip::class,
    parentColumns = ["tripId"],
    childColumns = ["ownerTripId"],
    onDelete = ForeignKey.CASCADE))],
    indices = [
    Index("ownerSeqNum"),
    Index("ownerTripId")])
data class Form(
    @PrimaryKey(autoGenerate = true)
    var formId: Long = 0,
    var productType: String = "",
    var startDate: String = "",
    var startTime: String = "",
    var endDate: String = "",
    var endTime: String = "",
    var grossGallons: Double = 0.0,
    var netGallons: Double = 0.0,
    var initialMeterReading: Double = 0.0,
    var initialTrailerReading: Double = 0.0,
    var finalMeterReading: Double = 0.0,
    var finalTrailerReading: Double = 0.0,
    var notes: String = "",
    var billOfLading: String = "",
    var ownerSeqNum: Long = 0,
    var ownerTripId: Long = 0
)