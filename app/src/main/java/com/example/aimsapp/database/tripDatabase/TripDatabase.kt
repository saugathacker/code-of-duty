package com.example.aimsapp.database.tripDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * abstract class to create an instance of the database
 */
@Database(entities = [Trip::class, WayPoint::class, Form::class], version = 1, exportSchema = false)
abstract class TripDatabase : RoomDatabase() {
    abstract val dao: TripDao

    companion object {
        @Volatile
        private var INSTANCE: TripDatabase? = null

        /**
         * Function to get an instance of the database
         */
        fun getInstance(context: Context): TripDatabase {
            synchronized(this) {
                var instance = INSTANCE

                //using the singleton pattern to create database if database does not exist.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TripDatabase::class.java,
                        "TripsWithForms1"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}