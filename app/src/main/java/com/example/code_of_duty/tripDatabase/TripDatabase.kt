package com.example.code_of_duty.tripDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.code_of_duty.locationDatabase.SavedLocationDatabase

@Database(entities = [Trip::class,Point::class], version = 1, exportSchema = false)
abstract class TripDatabase: RoomDatabase() {
    abstract val tripDao: TripDao

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
                        "trips"
                    )
                        .build()

                    INSTANCE = instance
                }

                return instance

            }
        }
    }
}