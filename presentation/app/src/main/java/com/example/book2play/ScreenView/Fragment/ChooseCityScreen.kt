package com.example.book2play.ScreenView.Fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.LogicConnection.Handler.ApiHandler

import com.example.Type.MyBookingModel
import com.example.Type.MyCityModel
import com.example.book2play.ScreenView.Activity.ChooseCenterScreen
import com.example.book2play.R


class ChooseCityScreen : Fragment() {
    internal lateinit var citySpinner : Spinner
    private var mLocationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    var cities = ArrayList<MyCityModel>()
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?)
            : View? {
        // inflate
        val v = inflater.inflate(R.layout.screen_choose_city,container, false)
        citySpinner = v.findViewById(R.id.CitySpinner) as Spinner
        val btnContinue = v.findViewById(R.id.btnContinue) as Button
        val btnCurrentLoc = v.findViewById(R.id.btnCurrentLoc) as Button

        val citiesName = getCityNameList()
        val adapter = activity?.let { ArrayAdapter(it, R.layout.spinner_textview, citiesName) }
        citySpinner.adapter = adapter

        btnContinue.setOnClickListener {
            val text = citySpinner.selectedItem.toString()
            val intent = Intent(activity, ChooseCenterScreen::class.java)
            val bookingInfor = MyBookingModel(city = text)
            Log.d("make booking", "ChooseLocationScreen: "+bookingInfor.toString())
            intent.putExtra("BookingInfo", bookingInfor)
            intent.putExtra("City", getCityFromName(text))
            startActivity(intent)
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

    private fun getCityNameList() : ArrayList<String?>{
        cities = activity?.let { ApiHandler.getCityList(it) }!!
        var citiesName = ArrayList<String?>()
        // get name set from cities
        if (cities != null) {
            for (city in cities){
                citiesName.add(city.cityName)
            }
        }
        return citiesName
    }

    private fun getCityFromName(name: String) : MyCityModel?{
        for (city in cities) {
            if (name == city.cityName){
                return city
            }
        }
        return null
    }
}
