package com.example.CreateBooking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.LogicConnection.Type.MyBookingModel
import com.example.book2play.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Location2Screen : AppCompatActivity(),
    OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    var IMAGES = intArrayOf(
        R.drawable.hcmc1,
        R.drawable.hcmc2,
        R.drawable.hcmc3
    )
    var NAMES = arrayOf(
        "PhatCho Sporting Center",
        "HieuLon Sporting Center",
        "BaoDoi Sporting Center"
    )
    var DESCRIPTIONS = arrayOf(
        "199 Phan Xich Long Street, Binh Thanh District",
        "145 Le Duan Street, District 1",
        "59 Dien Bien Phu Street, District 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location2)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel

        val listView =
            findViewById<View>(R.id.listPlaces) as ListView

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        val toolbar =
            findViewById<View>(R.id.toolbarLS2) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Ho Chi Minh city"
        val adapter = MyAdapter(this, NAMES, DESCRIPTIONS, IMAGES)

        listView.adapter = adapter
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->

                    val theCenterName = NAMES[position]
                    val intent = Intent(applicationContext, SearchDayScreen::class.java)
                    // update bookingInfo
                    if (bookingInfo != null) {
                        bookingInfo.center = theCenterName
                    }
                    else {
                        val bookingInfo =
                            MyBookingModel(center = theCenterName)
                    }
                    intent.putExtra("BookingInfo", bookingInfo)
                    intent.putExtra("CENTERNAME", theCenterName)
                    startActivity(intent)

            }
    }

    internal inner class MyAdapter(
        context: Context,
        var Name: Array<String>,
        var Description: Array<String>,
        var Images: IntArray
    ) :
        ArrayAdapter<String?>(context,
            R.layout.rows_listview,
            R.id.textViewName, Name) {
        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {
            val layoutInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val row = layoutInflater.inflate(R.layout.rows_listview, parent, false)
            val images = row.findViewById<ImageView>(R.id.image)
            val centerName = row.findViewById<TextView>(R.id.textViewName)
            val description = row.findViewById<TextView>(R.id.textViewDescription)
            images.setImageResource(Images[position])
            centerName.text = Name[position]
            description.text = Description[position]
            return row
        }

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
}