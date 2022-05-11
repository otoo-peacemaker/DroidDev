package com.example.hydrateme

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = 29)
class MainActivityTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    @Test
    fun verifyDoneStr() {
        val doneStr: String = context.getString(R.string.done)
        Assert.assertEquals(0, doneStr.indexOf("You are done for today "))
    }

}