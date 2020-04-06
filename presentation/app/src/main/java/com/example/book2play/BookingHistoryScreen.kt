package com.example.book2play

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.booking_history_screen.*

class BookingHistoryScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_history_screen)
        toolBar.setTitle("Booking history")
        setSupportActionBar(toolBar)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)
    }
}
