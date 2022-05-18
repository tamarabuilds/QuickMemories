package com.example.quickmemories

import android.view.View
import android.widget.ListView
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.quickmemories.adapter.ChildListAdapter
import com.example.quickmemories.data.Child
import kotlinx.coroutines.NonCancellable.children
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.w3c.dom.Text


class CustomMatchers {
    companion object {
        fun withItemCount (count: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>
                (RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("RecyclerView with item count: $count")
                }

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    return item?.adapter?.itemCount == count
                }
                }
        }

        fun confirmNameWithChildInViewHolder(childName: String): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView> (
                RecyclerView::class.java
            ) {
                override fun describeTo(description: Description?) {
                    description?.appendText(childName)
                }

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    val count = item?.childCount

                    for (i in 0 until count!!) {
                        val viewHolder = item.findViewHolderForAdapterPosition(i)
                                as ChildListAdapter.ChildViewHolder
                        if (viewHolder.binding.childName.text == childName) {
                            return true
                        }
                    }
                    return false
                }

            }

        }

    }
}
