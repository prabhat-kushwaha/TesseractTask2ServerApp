package com.prabhatkushwaha.task2serverapp

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder

class MobileRotationService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

   private var mLocationString = ""
    private val mBinder = object : IAIDLAppRotation.Stub() {
        override fun getRotation(): String {
            val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            val mSensor = sensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR)
            sensorManager.registerListener(object : SensorEventListener {
                override fun onSensorChanged(p0: SensorEvent?) {
                    p0?.sensor?.type.let {

                        mLocationString =
                            "\nX:${p0?.values?.get(0)}\n Y:${p0?.values?.get(1)} \nZ:${p0?.values?.get(2)}"
                    }
                }

                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                }
            }, mSensor[0], 8000)

            return mLocationString
        }
    }
}