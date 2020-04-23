package com.example.book2play.BookingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.book2play.R
import kotlinx.android.synthetic.main.screen_booking_history.*

class BookingHistoryScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.screen_booking_history, container, false)


        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragmentAdapter =
            MyPagerAdapter(this.childFragmentManager)

        viewPager.adapter = fragmentAdapter


        tabLayout.setupWithViewPager(viewPager)
    }

}
