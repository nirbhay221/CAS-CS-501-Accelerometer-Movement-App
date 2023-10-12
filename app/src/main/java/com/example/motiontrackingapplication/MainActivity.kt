package com.example.motiontrackingapplication

import android.content.Context
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() ,SensorEventListener{
    private var sensorManager: SensorManager ?= null
    private var sensor: Sensor ?= null
    private lateinit var xVal:TextView
    private lateinit var yVal:TextView
    private lateinit var zVal:TextView
    private lateinit var seekBar : SeekBar
    private lateinit var displaySensitivity: TextView
    var sensitivity = 1.0;
    @RequiresApi(Build.VERSION_CODES.O)
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
        seekBar = findViewById(R.id.seekBar)
        displaySensitivity = findViewById(R.id.textView)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        updateTextViews(sensitivity)
        seekBar.max = 100
        seekBar.progress = 1
        seekBar.min = 0
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                TODO("Not yet implemented")
                if(fromUser){
                    sensitivity = progress.toDouble()
                    updateTextViews(sensitivity)

                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                TODO("Not yet implemented")
            }
        })
    }
    private fun updateTextViews(sensitivity: Double) {
        displaySensitivity.text = "${sensitivity}"
        Toast.makeText(this,"Threshold : ${sensitivity}",Toast.LENGTH_LONG).show()
    }

    private fun makeToast(message:String){
        Toast.makeText(this,"${message}",Toast.LENGTH_LONG).show()
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
               Sensor.TYPE_LINEAR_ACCELERATION-> {
                   var x: Float = event.values[0]
                   var y: Float = event.values[1]
                   var z: Float = event.values[2]


                   xVal.text = x.toString()
                   yVal.text = y.toString()
                   zVal.text = z.toString()
                   var magnitude = Math.sqrt((x*x+y*y+z*z).toDouble())
                   if(magnitude > sensitivity){

                   }


               }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
    }
}