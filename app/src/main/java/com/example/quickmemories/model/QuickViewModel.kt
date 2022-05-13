package com.example.quickmemories.model

import android.content.ClipData
import androidx.lifecycle.*
import com.example.quickmemories.data.Child
import com.example.quickmemories.data.ChildMemoryDao
import com.example.quickmemories.data.Memory
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class QuickViewModel(private val childMemoryDao: ChildMemoryDao) : ViewModel() {

    // Cache all children and memories from the database using LiveData.
    val allChildren: LiveData<List<Child>> = childMemoryDao.getChildren().asLiveData()
    val allMemories: LiveData<List<Memory>> = childMemoryDao.getMemories().asLiveData()

    /**
     * Updates an existing Child in the database.
     */
    fun updateChild(
        childId: Int,
        childName: String,
        childDob: String
    ) {
        val updatedChild = getUpdatedChildEntry(childId, childName, childDob)
        updateChild(updatedChild)
    }

    fun updateMemory(
        memoryId: Int,
        childId: Int,
        memoryDate: String,
        memoryText: String
    ) {
        val updatedMemory = getUpdatedMemoryEntry(memoryId, childId, memoryDate, memoryText)
        updateMemory(updatedMemory)
    }

    /**
     * Launching a new coroutine to update a child or memory in a non-blocking way
     */
    private fun updateChild(child: Child) {
        viewModelScope.launch {
            childMemoryDao.update(child)
        }
    }

    private fun updateMemory(memory: Memory) {
        viewModelScope.launch {
            childMemoryDao.update(memory)
        }
    }



    /**
     * Inserts the new Child into database.
     */
    fun addNewChild(childName: String, childDob: String) {
        val newChild = getNewChildEntry(childName, childDob)
        insertChild(newChild)
    }

    /**
     * Inserts the new Memory into database.
     */
    fun addNewMemory(childId: Int, memoryDate: String, memoryDetails: String) {
        val newMemory = getNewMemoryEntry(childId, memoryDate, memoryDetails)
        insertMemory(newMemory)
    }


    /**
     * Launching a new coroutine to insert a child in a non-blocking way
     */
    private fun insertChild(child: Child) {
        // start a coroutine
        viewModelScope.launch {
            childMemoryDao.insert(child)
        }
    }

    /**
     * Launching a new coroutine to insert a memory in a non-blocking way
     */
    private fun insertMemory(memory: Memory) {
        // start a coroutine
        viewModelScope.launch {
            childMemoryDao.insert(memory)
        }
    }


    /**
     * Launching a new coroutine to delete a child or memory in a non-blocking way
     */
    fun deleteChild(child: Child) {
        viewModelScope.launch {
            childMemoryDao.delete(child)
        }
    }

    fun deleteMemory(memory: Memory) {
        viewModelScope.launch {
            childMemoryDao.delete(memory)
        }
    }

    /**
     * Retrieve a child or memory from the repository.
     */
    fun retrieveChild(childId: Int): LiveData<Child> {
        return childMemoryDao.getChild(childId).asLiveData()
    }

    fun retreiveMemory(memoryId: Int): LiveData<Memory> {
        return childMemoryDao.getMemory(memoryId).asLiveData()
    }





    /**
     * Returns an instance of the [Child] entity class with the child info entered by the user.
     * This will be used to add a new entry to the Child database.
     */
    private  fun getNewChildEntry(childName: String, childDob: String): Child {
        return Child(
            childName = childName,
            childDob = childDob
        )
    }



    /**
     * Returns an instance of the [Memory] entity class with the memory info entered by the user.
     * This will be used to add a new entry to the Memory database.
     */
    private fun getNewMemoryEntry(childId: Int, memoryDate: String, memoryDetails: String): Memory {
        return Memory(
            childId = childId,
            memoryDate = memoryDate,
            memoryText = memoryDetails
        )
    }






    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValidChild(childName: String, childDob: String): Boolean {
        if (childName.isBlank() || childDob==null ) {
            return false
        }
        return true
    }

    fun isEntryValidMemory(childId: Int, memoryDate: String,memoryText: String): Boolean {
        if (childId==null || memoryDate==null || memoryText.isBlank()) {
            return false
        }
        return true
    }



    /**
     * Called to update an existing entry in the Child and Memory database.
     * Returns an instance of the [Child] or [Memory] entity class with the child or
     * memory info updated by the user.
     */
    private fun getUpdatedChildEntry(
        childId: Int,
        childName: String,
        childDob: String,
    ): Child {
        return Child(
            childId = childId,
            childName = childName,
            childDob = childDob,
        )
    }

    private fun getUpdatedMemoryEntry(
        memoryId: Int,
        childId: Int,
        memoryDate: String,
        memoryText: String
    ) : Memory {
        return Memory(
            memoryId = memoryId,
            childId = childId,
            memoryDate = memoryDate,
            memoryText = memoryText
        )
    }

}



// This will instantiate the Quick View Model
class QuickViewModelFactory(private val childMemoryDao: ChildMemoryDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuickViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuickViewModel(childMemoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}