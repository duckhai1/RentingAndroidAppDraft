package com.example.book2play.ScreenView.Activity

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LogicConnection.Handler.ApiHandler

import com.example.Type.MyBookingModel
import com.example.Type.MyCenterModel
import com.example.Type.MyCityModel
import com.example.book2play.Adapter.ChooseCenterAdapter
import com.example.book2play.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.screen_choose_center.*

class ChooseCenterScreen : AppCompatActivity(),OnMapReadyCallback {
    var bookingInfo :MyBookingModel? = MyBookingModel()
    var city : MyCityModel? = null
    var centers = ArrayList<MyCenterModel>()
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_choose_center)

        // get last intent information
        bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("make booking", "Location2Screen: " +bookingInfo.toString())
        city = intent.getSerializableExtra("City") as? MyCityModel
        Log.d("Map", city.toString())

        // Map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        val toolbar = findViewById<View>(R.id.toolbarLS2) as Toolbar
        toolbar.setTitle("Select court in " + bookingInfo?.city)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val centers = getCenterList(bookingInfo!!.city.toString())
        listPlaces.layoutManager = LinearLayoutManager(this)
        listPlaces.adapter = ChooseCenterAdapter(
            centers,
            this,
            bookingInfo
        )
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        if (city != null) {
            val cityLocation = LatLng(city!!.latitude, city!!.longitude)
            val zoomLevel = 13.0f
//            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, zoomLevel))

            // get current location
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        val curLocation = LatLng(location?.latitude, location?.longitude)
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, zoomLevel))
                    } else {
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, zoomLevel))
                    }
                }

            drawCenterMarker(mMap!!)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun drawCenterMarker(googleMap: GoogleMap){
        for (center in centers){
            val centerLocation = LatLng(center.latitude, center.longitude)
            googleMap.addMarker(MarkerOptions().position(centerLocation).title(center.centerName))
        }
    }

    private fun getCenterList(cityName : String) : ArrayList<MyCenterModel>{
        centers = ApiHandler.getSportCenterInCity(this, cityName)
        return centers
    }
}
