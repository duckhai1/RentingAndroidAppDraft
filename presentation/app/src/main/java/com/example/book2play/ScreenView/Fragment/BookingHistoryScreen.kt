package com.example.book2play.ScreenView.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.LogicConnection.Handler.ApiHandler
import com.example.Type.MyBookingModel
import com.example.book2play.Adapter.MyPagerAdapter
import com.example.book2play.MyApplication
import com.example.book2play.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.screen_booking_history.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookingHistoryScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.screen_booking_history, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // divide bookingList to 2 type
        val bookingList = adding_list()
        val bookingType = getBookingTypeList()
        val upcommingBooking = ArrayList<MyBookingModel>()
        val completedBooking = ArrayList<MyBookingModel>()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val today = Date()
        for (booking in bookingList){
            val bookingDay = sdf.parse(booking.date)
            if (bookingDay.before(today)){
                completedBooking.add(booking)
            } else {
                upcommingBooking.add(booking)
            }
        }

        // setting up each booking type fragment
        val fragmentAdapter = MyPagerAdapter(this.childFragmentManager)
        for (type in bookingType){
            when (type){
                "Upcomming" -> {
                    val fragment = UpcommingFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("BookingList", upcommingBooking)
                    fragment.arguments = bundle
                    fragmentAdapter.addFrag(fragment, "Upcomming")
                }
                "Completed" ->{
                    val fragment = CompletedFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("BookingList", completedBooking)
                    fragment.arguments = bundle
                    fragmentAdapter.addFrag(fragment, "Completed")
                }
            }
        }

        // setting view pager layout
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        if (bookingType.size < 3) {
            tabLayout.tabMode = TabLayout.MODE_FIXED
        } else {
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        }
    }

    fun getBookingTypeList() : ArrayList<String>{
        val arrayList = ArrayList<String>()
        arrayList.add("Upcomming")
        arrayList.add("Completed")
        return arrayList
    }

    fun adding_list(): ArrayList<MyBookingModel>{


        val playerId = (activity?.application as MyApplication).getplayerId()
        val arrayList = ApiHandler.getBookingOfPlayer(activity!!, playerId)
//        arrayList.add(
//            MyBookingModel(
//                "2020-03-23",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "TA Sport Center",
//                "Court 1",
//                "Maria Onawa",
//                true
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2020-06-23",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "TA Sport Center",
//                "Court 1",
//                "Maria Onawa",
//                false
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2020-04-02",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "TA Sport Center",
//                "Court 1",
//                "Maria Onawa",
//                true
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2010-01-12",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "TA Sport Center",
//                "Court 1",
//                "Maria Onawa",
//                true
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2021-03-23",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "TA Sport Center",
//                "Court 1",
//                "Maria Onawa",
//                true
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2020-06-23",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "center1",
//                "court1",
//                "MariaOnawa",
//                false
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2020-12-23",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "center1",
//                "court1",
//                "MariaOnawa",
//                false
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2020-02-22",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "center1",
//                "court1",
//                "MariaOnawa",
//                false
//            )
//        )
//        arrayList.add(
//            MyBookingModel(
//                "2020-02-17",
//                "10:30:00",
//                "11:30:00",
//                "city1",
//                "center1",
//                "court1",
//                "MariaOnawa",
//                true
//            )
//        )

        return arrayList
    }
}
