package com.example.CreateBooking

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.book2play.MyBookingModel
import com.example.book2play.R


class ChooseLocationScreen : Fragment() {
    internal lateinit var citySpinner : Spinner
    private var mLocationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.choose_location_screen,container, false)

        citySpinner = v.findViewById(R.id.CitySpinner) as Spinner
        val btnContinue = v.findViewById(R.id.btnContinue) as Button
        val btnCurrentLoc = v.findViewById(R.id.btnCurrentLoc) as Button


        val cities = arrayOf("Ho Chi Minh city", "My Tho city", "Vung Tau city", "Binh Duong city")
        val adapter = activity?.let { ArrayAdapter(it, R.layout.spinner_textview, cities) }
        citySpinner.adapter = adapter

        btnContinue.setOnClickListener {
            val text = citySpinner.selectedItem.toString()
            if (text === "My Tho city" || text === "Vung Tau city" || text === "Binh Duong city") {
                Toast.makeText(activity, "Please wait for the update!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, Location2Screen::class.java)
                val bookingInfor = MyBookingModel(city = text)
                intent.putExtra("BookingInfo", bookingInfor)
                startActivity(intent)
            }
        }

        btnCurrentLoc.setOnClickListener{
            getLocationPermission()
        }
        return v
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false
        if (activity?.applicationContext?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        }
    }
}
