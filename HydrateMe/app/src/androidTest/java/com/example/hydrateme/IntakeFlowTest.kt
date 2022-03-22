package com.example.hydrateme

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntakeFlowTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testIntakeFlow() {
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editTextTextPersonName)).perform(typeText("1000"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.total)).check(matches(withText("1000 ml")))
    }
}