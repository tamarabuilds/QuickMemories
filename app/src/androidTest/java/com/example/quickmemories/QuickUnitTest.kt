package com.example.quickmemories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.widget.TextView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*

@RunWith(AndroidJUnit4::class)
class QuickUnitTest {

    // Constants for testing without needing to call an instance
    companion object {
        const val TEST_CHILD_NAME = "eljkd"
        val TEST_DATE_OF_BIRTH = "2022-02-05"           // LocalDate.parse("2022-02-01")
        const val ACTION_BAR = "action_bar"
    }

    //private val viewModel = QuickViewModel()

    @get:Rule
    var activity = ActivityScenarioRule(MainActivity::class.java)

    // Helper functions that other tests can reference
    private fun clickAddChild() {
        onView(withId(R.id.button_add_child)).perform(click())
    }

    private fun enterAndSaveChildDetails() {
        onView(withId(R.id.child_name_add))
            .perform(typeText(TEST_CHILD_NAME))
        onView(withId(R.id.child_dob_add))
            .perform(typeText(TEST_DATE_OF_BIRTH))
        onView(withId(R.id.button_save_child)).perform(click())
    }



    @Test
    fun add_child() {
        clickAddChild()
        enterAndSaveChildDetails()

        // Check that test child created
  //      onView(withId(R.id.recyclerViewChildList))
 //           .check(matches(withText(containsString(TEST_CHILD_NAME))))

    }
}

//