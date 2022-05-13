package com.example.quickmemories.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildMemoryDao {
    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the new item.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(child: Child)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(memory: Memory)

    @Update
    suspend fun update(child: Child)

    @Update
    suspend fun update(memory: Memory)

    @Delete
    suspend fun delete(child: Child)

    @Delete
    suspend fun delete(memory: Memory)

    // Now query the DB
    @Query("SELECT * from child WHERE childId = :id")
    fun getChild(id: Int): Flow<Child>

    @Query("SELECT * from child ORDER BY name ASC")
    fun getChildren(): Flow<List<Child>>

    @Query("SELECT * from memory WHERE memoryId = :id")
    fun getMemory(id: Int): Flow<Memory>

    @Query("SELECT * from memory ORDER BY memory_date DESC")
    fun getMemories(): Flow<List<Memory>>
}