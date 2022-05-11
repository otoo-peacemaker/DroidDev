package com.example.hydrateme

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateEntryActivityTest {
    @Test
    fun test_result() {
        val scenario = launchActivity<CreateEntryActivity>()
        onView(withId(R.id.editTextTextPersonName)).perform(typeText("100"))
        onView(withId(R.id.button)).perform(click())

        val result = scenario.result.resultData
        val intakeEntry = result.getIntExtra("intake",0)

        Assert.assertEquals(100, intakeEntry)
    }

}