package com.example.BookingList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.LogicConnection.Type.MyBookingModel
import com.example.book2play.R
import kotlinx.android.synthetic.main.fragment_cancelled.*

/**
 * A simple [Fragment] subclass.
 */
class CancelledFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cancelled, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arrayList = ArrayList<MyBookingModel>()
        adding_upcomming_list(arrayList)

        cancel_booking.layoutManager = LinearLayoutManager(activity)
        cancel_booking.addItemDecoration(
            MarginItemDecoration(
                32,
                64
            )
        )
        cancel_booking.adapter = context?.let {
            CancelledAdapter(
                arrayList,
                it
            )
        }
    }


    fun adding_upcomming_list(arrayList : ArrayList<MyBookingModel>){
        arrayList.add(
            MyBookingModel(
                "Jan 2",
                "10h30",
                "11h30",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "Mar 13",
                "10h30",
                "11h30",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "Mar 25",
                "10h30",
                "11h30",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "May 8",
                "10h30",
                "11h30",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "Jul 29",
                "10h30",
                "11h30",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )
        arrayList.add(
            MyBookingModel(
                "Dec 1",
                "10h30",
                "11h30",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa"
            )
        )

    }
}
