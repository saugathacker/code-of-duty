package com.example.code_of_duty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedLocation::class], version = 1, exportSchema = false)
abstract class SavedLocationDatabase : RoomDatabase(){
    abstract val savedLocationDao: SavedLocationDao

    companion object {
        @Volatile
        private var INSTANCE: SavedLocationDatabase? = null

        fun getInstance(context: Context): SavedLocationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SavedLocationDatabase::class.java,
                        "saved_location_database"
                    )
                        .build()

                    INSTANCE = instance
                }

                return instance

            }
        }
    }
}