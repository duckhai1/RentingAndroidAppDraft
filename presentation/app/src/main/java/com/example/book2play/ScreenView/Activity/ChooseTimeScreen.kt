package com.example.book2play.ScreenView.Activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.Type.MyBookingModel
import com.example.Type.MyCourtModel
import com.example.book2play.Adapter.ChooseTimePagerAdapter
import com.example.book2play.ScreenView.Fragment.ChooseTimeCourtFragment
import com.example.book2play.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.screen_choose_time.*


class ChooseTimeScreen : AppCompatActivity() {
    var bookingInfo : MyBookingModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_choose_time)

        // get last intent information
        bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("make booking", "SelectTimeScreen: "+bookingInfo.toString())
        val bundle = intent.extras

        toolBar.setTitle("Choose your time slot")
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val courts = getCourtList()

        val fragmentAdapter =
            ChooseTimePagerAdapter(
                supportFragmentManager
            )
        for (court in courts){
            val court_frag =
                ChooseTimeCourtFragment()
            var cbundle = bundle?.clone() as Bundle
            if (cbundle != null) {
                cbundle.putString("courtName", court.courtName)
            }
            court_frag.arguments = cbundle
            court.courtName?.let { fragmentAdapter.addFrag(court_frag, it) }
        }

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        if (courts.size < 3) {
            tabLayout.tabMode = TabLayout.MODE_FIXED
        } else {
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        }


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun getCourtList() : ArrayList<MyCourtModel>{
        val courts = ArrayList<MyCourtModel>()

        // TODO change with get courts from server
        // ApiHandler.getCourt
        courts.add(MyCourtModel("court1", "center1", "city1"))
        courts.add(MyCourtModel("court2", "center1", "city1"))
        courts.add(MyCourtModel("court3", "center1", "city1"))
        courts.add(MyCourtModel("court4", "center1", "city1"))

        return courts
    }

}

