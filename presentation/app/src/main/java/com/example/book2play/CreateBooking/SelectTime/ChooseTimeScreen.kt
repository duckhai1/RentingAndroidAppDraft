package com.example.book2play.CreateBooking.SelectTime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.Type.MyBookingModel
import com.example.book2play.R
import kotlinx.android.synthetic.main.screen_choose_time.*

class ChooseTimeScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_choose_time)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("make booking", "SelectTimeScreen: "+bookingInfo.toString())

        toolBar.setTitle("Booking")
        setSupportActionBar(toolBar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)



        // TODO change to more flexible to add court
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        val bundle = intent.extras

        setupFragment(fragmentAdapter,bundle, "Court 1")
        setupFragment(fragmentAdapter,bundle, "Court 2")
        setupFragment(fragmentAdapter,bundle, "Court 3")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)



    }


    fun setupFragment(fragmentAdapter: MyPagerAdapter, bundle: Bundle?, court_name : String){
        var court_frag = when (court_name){
            "Court 1" -> Court1Fragment()
            "Court 2" -> Court1Fragment()   // -> need to change
            "Court 3" -> Court1Fragment()   // -> need to change
            else -> Court3Fragment()
        }
        var cbundle = bundle?.clone() as Bundle
        if (cbundle != null) {
            cbundle.putString("courtName", court_name)
        }
        court_frag.arguments = cbundle
        fragmentAdapter.addFrag(court_frag, court_name)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}

