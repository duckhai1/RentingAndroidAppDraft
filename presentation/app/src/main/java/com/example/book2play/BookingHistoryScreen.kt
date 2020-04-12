package com.example.book2play

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.booking_history_screen.*
import kotlinx.android.synthetic.main.booking_history_screen.view.*

class BookingHistoryScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.booking_history_screen, container, false)


        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragmentAdapter = MyPagerAdapter(this.childFragmentManager)

        viewPager.adapter = fragmentAdapter


        tabLayout.setupWithViewPager(viewPager)
    }

}
