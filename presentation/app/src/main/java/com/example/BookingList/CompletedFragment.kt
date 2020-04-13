package com.example.BookingList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book2play.MyBookingModel
import com.example.book2play.R
import kotlinx.android.synthetic.main.fragment_completed.*

/**
 * A simple [Fragment] subclass.
 */
class CompletedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arrayList = ArrayList<MyBookingModel>()
        adding_upcomming_list(arrayList)

        completed_booking.layoutManager = LinearLayoutManager(activity)
        completed_booking.addItemDecoration(
            MarginItemDecoration(
                32,
                64
            )
        )
        completed_booking.adapter = context?.let {
            CompletedAdapter(
                arrayList,
                it
            )
        }
    }


    fun adding_upcomming_list(arrayList : ArrayList<MyBookingModel>){
        arrayList.add(
            MyBookingModel(
                "Jan 2",
                "Thu",
                "10h30-11h30",
                "",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa",
                1
            )
        )
        arrayList.add(
            MyBookingModel(
                "Jan 2",
                "Thu",
                "10h30-11h30",
                "",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa",
                0
            )
        )
        arrayList.add(
            MyBookingModel(
                "Mar 25",
                "Mon",
                "10h30-11h30",
                "",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa",
                1
            )
        )
        arrayList.add(
            MyBookingModel(
                "May 8",
                "Tue",
                "10h30-11h30",
                "",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa",
                1
            )
        )
        arrayList.add(
            MyBookingModel(
                "Jul 29",
                "Fri",
                "10h30-11h30",
                "",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa",
                1
            )
        )
        arrayList.add(
            MyBookingModel(
                "Dec 1",
                "Sun",
                "10h30-11h30",
                "",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa",
                1
            )
        )

    }
}
