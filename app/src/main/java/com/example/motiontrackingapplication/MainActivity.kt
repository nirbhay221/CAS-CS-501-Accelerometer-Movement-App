package com.example.motiontrackingapplication

import android.content.Context
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() ,SensorEventListener{
    private var sensorManager: SensorManager ?= null
    private var sensor: Sensor ?= null
    private lateinit var xVal:TextView
    private lateinit var yVal:TextView
    private lateinit var zVal:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape)
        } else {
            setContentView(R.layout.activity_main)
        }
        xVal = findViewById(R.id.xId)
        yVal = findViewById(R.id.yId)
        zVal = findViewById(R.id.zId)



        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape)

            Toast.makeText(this,"changed to landscape", Toast.LENGTH_LONG)
        } else {
            setContentView(R.layout.activity_main)
            Toast.makeText(this,"changed to portrait", Toast.LENGTH_LONG)
        }}

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
    override fun onSensorChanged(event: SensorEvent?) {
//        TODO("Not yet implemented")
           when(event?.sensor?.type){
               Sensor.TYPE_ACCELEROMETER -> {
                   xVal.text = event!!.values[0].toString()
                   yVal.text = event!!.values[1].toString()
                   zVal.text = event!!.values[2].toString()

               }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
    }
}