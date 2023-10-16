package com.example.motiontrackingapplication

import androidx.activity.viewModels
import androidx.lifecycle.SavedStateHandle
import org.junit.Test
import org.junit.Before


import org.junit.Assert.*
import kotlin.math.abs

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var viewModel: AccelerometerViewModel

    @Before
    fun setup() {
        val savedStateHandle = SavedStateHandle()
        viewModel = AccelerometerViewModel(savedStateHandle)
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_max_accelaration() {
        val x:Float = -7.4F
        val y = 3.1F
        val z = 6.5F

        val result = viewModel.maxAccelaration(x, y, z)

        assertEquals(result,abs(x))
    }

    @Test
    fun test_float_to_string() {
        val x:Float = 7.4F

        val result = viewModel.returnStringFromFloat(x)

        assertEquals(result,"7.4")
    }

}