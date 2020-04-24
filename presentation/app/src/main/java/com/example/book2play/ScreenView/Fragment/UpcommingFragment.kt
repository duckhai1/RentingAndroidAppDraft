package com.example.book2play.ScreenView.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.Type.MyBookingModel
import com.example.book2play.Adapter.UpcommingAdapter
import com.example.book2play.DisplayHelper.MarginItemDecoration
import com.example.book2play.R
import kotlinx.android.synthetic.main.fragment_upcomming.*

/**
 * A simple [Fragment] subclass.
 */
class UpcommingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_upcomming, container, false)
        // Inflate the layout for this fragment
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arrayList = ArrayList<MyBookingModel>()
        adding_upcomming_list(arrayList)

        upcomming_booking.layoutManager = LinearLayoutManager(activity)
        upcomming_booking.addItemDecoration(
            MarginItemDecoration(
                32,
                64
            )
        )
        upcomming_booking.adapter = context?.let {
            UpcommingAdapter(
                arrayList,
                it
            )
        }
    }


    fun adding_upcomming_list(arrayList : ArrayList<MyBookingModel>){
        arrayList.add(
            MyBookingModel(
                "2020-03-23",
                "10:30:00",
                "11:30:00",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "2020-03-23",
                "10:30:00",
                "11:30:00",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "2020-03-23",
                "10:30:00",
                "11:30:00",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "2020-03-23",
                "10:30:00",
                "11:30:00",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "2020-03-23",
                "10:30:00",
                "11:30:00",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "2020-03-23",
                "10:30:00",
                "11:30:00",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
    }


}

