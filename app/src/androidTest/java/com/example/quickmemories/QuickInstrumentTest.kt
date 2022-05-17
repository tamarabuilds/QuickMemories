package com.example.quickmemories

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.quickmemories.CustomAssertions.Companion.hasItemCount
import com.example.quickmemories.CustomMatchers.Companion.withChildInViewHolder
import com.example.quickmemories.CustomMatchers.Companion.withItemCount
import org.junit.Rule



@RunWith(AndroidJUnit4::class)
class QuickInstrumentTest {

    // Constants for testing without needing to call an instance
    companion object {
        const val TEST_CHILD_NAME = "eljkd"
        const val TEST_DATE_OF_BIRTH = "2022-02-05"           // LocalDate.parse("2022-02-01")
        const val ACTION_BAR = "action_bar"
        const val COUNT = 2
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
    fun countChildren() {
        onView(withId(R.id.recyclerViewChildList))
            .check(matches(withItemCount(COUNT)))
    }


    @Test
    fun seeChildNameOnView() {
        onView(withId(R.id.recyclerViewChildList))
            .check(matches(withChildInViewHolder(TEST_CHILD_NAME)))
    }


    @Test
    fun countChildrenWithViewAssertion() {
        onView(withId(R.id.recyclerViewChildList))
            .check(hasItemCount(COUNT))
    }

}

//
