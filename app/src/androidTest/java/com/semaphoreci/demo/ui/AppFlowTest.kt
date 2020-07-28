package com.semaphoreci.demo.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.semaphoreci.demo.R
import com.semaphoreci.demo.ui.repolist.RepoListActivity
import com.semaphoreci.demo.ui.repolist.RepoViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppFlowTest {

    @get:Rule
    val launchActivityRule = activityScenarioRule<RepoListActivity>()

    @Test
    fun appFlowTest() {
        // Wait for request to get a response
        Thread.sleep(20000)

        // Click on the first item of the list
        onView(withId(R.id.repo_list))
            .perform(actionOnItemAtPosition<RepoViewHolder>(0, click()))

        // Check that the next screen is open
        onView(withId(R.id.repo_description)).check(matches(isDisplayed()))
    }
}
