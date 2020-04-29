package com.example.book2play.ScreenView.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LogicConnection.Handler.ApiHandler

import com.example.Type.MyBookingModel
import com.example.Type.MyCenterModel
import com.example.book2play.Adapter.ChooseCenterAdapter
import com.example.book2play.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.screen_choose_center.*

class ChooseCenterScreen : AppCompatActivity(),OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_choose_center)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("make booking", "Location2Screen: " +bookingInfo.toString())

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
        val hcmc = LatLng(10.82302, 106.62965)
        mMap!!.addMarker(MarkerOptions().position(hcmc).title("Marker in HCMC"))
        val zoomLevel = 11.0f
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmc, zoomLevel))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getCenterList(cityName : String) : ArrayList<MyCenterModel>{
        var centers = ArrayList<MyCenterModel>()
        centers = ApiHandler.getSportCenterInCity(this, cityName)
        return centers
    }
}
