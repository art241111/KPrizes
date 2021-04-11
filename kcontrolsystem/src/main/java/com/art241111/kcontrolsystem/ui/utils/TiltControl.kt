package com.art241111.kcontrolsystem.ui.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class TiltControl(private val sensorManager: SensorManager) {
    lateinit var tiltMove: TiltMove
    private var sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private lateinit var listener: SensorEventListener

    fun startTracking() {
        Log.d("START", "START______________________________________________")
        if (sensor != null) {
            listener = if (this::tiltMove.isInitialized) {
                MySensorEventListener(tiltMove)
            } else {
                MySensorEventListener(null)
            }

            sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Log.e("sensor", "Sensor not detected")
        }
    }

    fun stopTracking() {
        if (sensor != null && this::listener.isInitialized) {
            sensorManager.unregisterListener(listener, sensor)
        } else {
            Log.e("sensor", "Sensor not detected")
        }
    }

    private class MySensorEventListener(val tiltMove: TiltMove?) : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (tiltMove != null) {
                if (event != null) {
                    tiltMove.move(
                        x = -1 * event.values[0].toDouble(),
                        y = -1 * event.values[1].toDouble()
                    )

                    Log.d(
                        "sensor",
                        "${-1 * event.values[0].toDouble()}: ${-1 * event.values[1].toDouble()}"
                    )
                }
            } else {
                Log.e("tilt_control_move", "[MoveInTime] is not initialized")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("sensor", "$sensor: $accuracy")
        }
    }
}
