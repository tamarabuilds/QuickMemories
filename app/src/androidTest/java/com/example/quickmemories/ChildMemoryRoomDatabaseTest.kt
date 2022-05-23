package com.example.quickmemories

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.room.CoroutinesRoom
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quickmemories.data.Child
import com.example.quickmemories.data.ChildMemoryDao
import com.example.quickmemories.data.ChildMemoryRoomDatabase
import com.example.quickmemories.model.QuickViewModel
import com.example.quickmemories.model.QuickViewModelFactory
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.attachChild
import kotlinx.coroutines.NonCancellable.children
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import java.io.IOException
import java.lang.Thread.*
import java.util.concurrent.CountDownLatch
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class ChildMemoryRoomDatabaseTest : TestCase() {

    // Constants for testing without needing to call an instance
    companion object {
        const val TEST_CHILD_NAME = "dbTestChild"
        const val TEST_DATE_OF_BIRTH = "2022-02-05"           // LocalDate.parse("2022-02-01")
    }

    private lateinit var childMemoryDao: ChildMemoryDao
    private lateinit var db: ChildMemoryRoomDatabase
    private lateinit var viewModel: QuickViewModel

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ChildMemoryRoomDatabase::class.java)

            // Allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
        childMemoryDao =db.childMemoryDao()
        viewModel = QuickViewModel(childMemoryDao)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    @Throws(Exception::class)
    fun writeAndReadChildMemory() = runBlocking {
        val countDown = CountDownLatch(1)

        // Creating an observer to watch for any changes in allChildren.
        // If there are changes, observer will be called.
        viewModel.allChildren.observeForever {
            Log.d("Testing", "Do we see this observer?")
            if (it.isNotEmpty()) {
                countDown.countDown()
            }
        }

        viewModel.addNewChild(TEST_CHILD_NAME, TEST_DATE_OF_BIRTH)

        // Wait for allChildren to add new child before this code continues
        countDown.await()

        val children = viewModel.allChildren.value
        if (children == null) {
            Log.d("Testing", "children list is null")
        }

        for (i in children!!) {
            if (i.childName == TEST_CHILD_NAME
                && i.childDob == TEST_DATE_OF_BIRTH
            ) {
                return@runBlocking
            }
        }
        assert(false)

    }




}