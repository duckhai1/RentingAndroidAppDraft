package com.example.locationsscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    internal lateinit var citySpinner : Spinner
    private var mLocationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        citySpinner = findViewById(R.id.CitySpinner) as Spinner
        val cities = arrayOf("Ho Chi Minh city", "My Tho city", "Vung Tau city", "Binh Duong city")
        val adapter = ArrayAdapter(this, R.layout.spinner_textview, cities)
        citySpinner.adapter = adapter

        btnContinue.setOnClickListener {
            val text = citySpinner.selectedItem.toString()
            if (text === "My Tho city" || text === "Vung Tau city" || text === "Binh Duong city") {
                Toast.makeText(this@MainActivity, "Please wait for the update!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, Location2Screen::class.java)
                startActivity(intent)
            }
        }

        btnCurrentLoc.setOnClickListener{
            getLocationPermission()
        }
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }
}
