package com.example.locationsscreen

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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailScreen : AppCompatActivity(){

    var NAMES = arrayOf(
        "Booking Time",
        "Location",
        "Court",
        "Booking Confirmation"
    )
    var DESCRIPTIONS = arrayOf(
        "April 27th, 9h30 - 10h30",
        "PhatCho Sporting Centre",
        "Court 3",
        "Confirmed"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_screen)
        val listView =
            findViewById<View>(R.id.listDetail) as ListView

        val toolbar = findViewById<View>(R.id.toolbarLS2) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Booking Detail"
        val adapter =
            MyAdapter(this, NAMES, DESCRIPTIONS)
        listView.adapter = adapter
    }

    internal inner class MyAdapter(
        context: Context,
        var Name: Array<String>,
        var Description: Array<String>
    ) :
        ArrayAdapter<String?>(context, R.layout.detail_listview, R.id.textViewName, Name) {
        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {
            val layoutInflater =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val row =
                layoutInflater.inflate(R.layout.detail_listview, parent, false)
            val centerName = row.findViewById<TextView>(R.id.textViewName)
            val description = row.findViewById<TextView>(R.id.textViewDescription)
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
}