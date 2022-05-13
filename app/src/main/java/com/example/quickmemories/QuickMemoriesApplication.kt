package com.example.quickmemories

import android.app.Application
import com.example.quickmemories.data.ChildMemoryRoomDatabase

class QuickMemoriesApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: ChildMemoryRoomDatabase by lazy {
        ChildMemoryRoomDatabase.getDatabase(this) }
}