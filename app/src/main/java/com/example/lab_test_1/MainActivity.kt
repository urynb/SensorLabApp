package com.example.lab_test_1

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sensorManager: SensorManager

    private lateinit var locationText: TextView
    private lateinit var outputText: TextView
    private lateinit var Button1: Button

    // Modify this
    // relevant variables for the light sensor -
    // you may need to change the type of these
    private var lightSensor: Sensor? = null
    private var latestReading: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationText = findViewById(R.id.locationText)
        outputText = findViewById(R.id.outputText)
        Button1 = findViewById(R.id.Button1)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Ask for location permission
        checkLocationPermission()


        // Modify this ~ to follow the lab
        // you need to convert this app from taking readings from the light sensor
        // to taking readings from the accelerometer
        // the light sensor only has 1 output (light in lux)
        // but the accelerometer will output as a tuple (X, Y, Z)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


        if (lightSensor == null) {
            outputText.text = "No light sensor on this device."
        }

        // Button press action
        Button1.setOnClickListener {
            latestReading?.let {
                outputText.text = "Light Level: $it lux"
            } ?: run {
                outputText.text = "No reading yet."
            }
        }

    }

    // location, don't modify
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        } else {
            getLocation()
        }
    }

    // location, don't modify
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                locationText.text = "Lat: %.5f\nLng: %.5f\nTime: %s".format(
                    location.latitude,
                    location.longitude,
                    timestamp
                )
            } else {
                locationText.text = "Unable to fetch location"
            }
        }
    }

    // location, don't modify
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            locationText.text = "Permission denied"
        }
    }

    // Modify these functions
    // sensor related readings,
    // edit these to follow the lab

    override fun onResume() {
        super.onResume()
        lightSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            latestReading = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed
    }
}