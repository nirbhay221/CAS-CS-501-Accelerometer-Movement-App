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
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlin.math.abs
import androidx.lifecycle.ViewModelProvider
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() ,SensorEventListener{
    private val accelerometerViewModel by viewModels<AccelerometerViewModel>()


    private var sensorManager: SensorManager ?= null
    private var sensor: Sensor ?= null
    private lateinit var xVal:TextView
    private lateinit var yVal:TextView
    private lateinit var zVal:TextView
    private lateinit var seekBar : SeekBar
    private lateinit var displaySensitivity: TextView
    var maxThreshold = 5.0
    private var xMovementToastShown = false;

    private var yMovementToastShown = false;

    private var zMovementToastShown = false;

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
        updateTextViews(accelerometerViewModel.threshold)
        seekBar.max = 100
        seekBar.progress = (accelerometerViewModel.threshold).toInt()
        seekBar.min = 0
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                TODO("Not yet implemented")
                if(fromUser){
                    accelerometerViewModel.threshold = progress.toDouble()
                    updateTextViews(accelerometerViewModel.threshold)
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
//        Toast.makeText(this,"Threshold : ${sensitivity}",Toast.LENGTH_LONG).show()
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
           if(event!!.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION){
                   var x: Float = event.values[0]
                   var y: Float = event.values[1]
                   var z: Float = event.values[2]
                   xVal.text = accelerometerViewModel.returnStringFromFloat(x)
                   yVal.text = accelerometerViewModel.returnStringFromFloat(y)
                   zVal.text = accelerometerViewModel.returnStringFromFloat(z)
                   val maxAcc:Float = accelerometerViewModel.maxAccelaration(x ,y ,z)
                   if(maxAcc>accelerometerViewModel.threshold){

                       if(maxAcc == abs(x) && !xMovementToastShown){
                           Toast.makeText(this,"Significant movement along the X-axis",Toast.LENGTH_LONG).show()
                           Log.d("Movement","Significant movement along the X-axis")
                           xMovementToastShown = true;
                           yMovementToastShown = false
                           zMovementToastShown = false;
                       }
                       else if(maxAcc == abs(y) && !yMovementToastShown){
                           Toast.makeText(this,"Significant movement along the Y-axis",Toast.LENGTH_LONG).show()
                           Log.d("Movement","Significant movement along the Y-axis")
                           yMovementToastShown = true
                           xMovementToastShown = false
                           zMovementToastShown = false
                       }
                       else if(maxAcc == abs(z) && !zMovementToastShown){
                           Toast.makeText(this,"Significant movement along the Z-axis",Toast.LENGTH_LONG).show()
                           Log.d("Movement","Significant movement along the Z-axis")
                           zMovementToastShown = true
                           xMovementToastShown = false
                           yMovementToastShown = false
                       }
                   }
               else{
                      xMovementToastShown = false;
                      yMovementToastShown = false;
                      zMovementToastShown = false;

                   }



               }
        }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
    }


}