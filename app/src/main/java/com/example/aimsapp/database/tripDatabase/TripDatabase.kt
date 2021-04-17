package com.example.aimsapp.database.tripDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aimsapp.database.tripDatabase.relations.DriverTruckCrossRef
import com.example.aimsapp.database.tripDatabase.relations.TruckTrailerCrossRef

@Database(entities = [Trip::class, WayPoint::class, Form::class, Driver::class, Account::class,
    Truck::class, Trailer::class, TruckTrailerCrossRef::class, DriverTruckCrossRef::class], version = 1, exportSchema = false)
abstract class TripDatabase : RoomDatabase() {
    abstract val dao: TripDao

    companion object {
        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getInstance(context: Context): TripDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TripDatabase::class.java,
                        "TripsWithForms"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}