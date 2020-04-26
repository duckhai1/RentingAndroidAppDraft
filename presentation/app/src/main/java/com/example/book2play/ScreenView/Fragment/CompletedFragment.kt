package com.example.book2play.ScreenView.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.Type.MyBookingModel
import com.example.book2play.Adapter.CompletedAdapter
import com.example.book2play.DisplayHelper.MarginItemDecoration
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
                "2020-03-23",
                "10:30:00",
                "11:30:00",
                "city1",
                "TA Sport Center",
                "Court 1",
                "Maria Onawa",
                true
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
                "Maria Onawa",
                false
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
                "Maria Onawa",
                true
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
                "Maria Onawa",
                true
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
                "Maria Onawa",
                true
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
                "Maria Onawa",
                true
            )
        )

    }
}
