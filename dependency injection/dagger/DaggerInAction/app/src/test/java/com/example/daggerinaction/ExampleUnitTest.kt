package com.example.daggerinaction

import com.example.daggerinaction.repository.local.LocalDataSource
import com.example.daggerinaction.repository.remote.RemoteDatasource
import com.example.daggerinaction.ui.login.LoginViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
     @Before
     fun setUp(){
     }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}