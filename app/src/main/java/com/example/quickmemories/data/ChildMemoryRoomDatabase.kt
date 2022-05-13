package com.example.quickmemories.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Child::class,
        Memory::class
    ],
    version = 2,
    exportSchema = false        // false so as not to keep schema version history backups
    )
abstract class ChildMemoryRoomDatabase : RoomDatabase(){

    abstract fun childMemoryDao(): ChildMemoryDao

    companion object {
        // Ensuring the value of the INSTANCE DB is always up to date with @Volatile
        @Volatile
        private var INSTANCE: ChildMemoryRoomDatabase? = null

        fun getDatabase(context: Context): ChildMemoryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChildMemoryRoomDatabase::class.java,
                    "child_memory_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}