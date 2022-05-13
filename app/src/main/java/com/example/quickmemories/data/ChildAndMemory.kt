package com.example.quickmemories.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * Entity data class represents a single row in the database.
 * Storing each child added to the database
 */

@Entity(tableName = "child")
data class Child(
    @PrimaryKey(autoGenerate = true)
    val childId: Int = 0,
    @ColumnInfo(name = "name")
    val childName: String,
    @ColumnInfo(name = "dob")
    val childDob: String,       // change from date for now
)




/**
 * Storing each memory
 */


@Entity(tableName = "memory", indices = [Index(value = ["memoryId", "childId"], unique = true) ])
data class Memory(
    @PrimaryKey(autoGenerate = true)
    val memoryId: Int = 0,
    @ColumnInfo(name = "childId")
    val childId: Int,
    @ColumnInfo(name = "memory_date")
    val memoryDate: String,     // change from date for now
    @ColumnInfo(name = "memory")
    val memoryText: String,
)

