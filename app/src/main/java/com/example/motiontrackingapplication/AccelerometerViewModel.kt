package com.example.motiontrackingapplication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.random.Random




private val CURRENT_THRESHOLD_KEY = "CURRENT_THRESHOLD_KEY"

class AccelerometerViewModel (private val savedStateHandle: SavedStateHandle):ViewModel(){
    var threshold: Double
        get()=savedStateHandle.get(CURRENT_THRESHOLD_KEY) ?: 5.0
        set(value) = savedStateHandle.set(CURRENT_THRESHOLD_KEY,value)

    fun maxAccelaration(x: Float, y: Float, z: Float): Float{
        return maxOf(abs(x), abs(y), abs(z))
    }

    fun returnStringFromFloat(x: Float): String {
        return x.toString()
    }
}