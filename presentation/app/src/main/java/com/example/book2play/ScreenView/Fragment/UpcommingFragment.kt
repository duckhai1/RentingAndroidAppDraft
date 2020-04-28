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
    var arrayList = ArrayList<MyBookingModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_upcomming, container, false)
        arrayList = this.arguments?.getSerializable("BookingList") as ArrayList<MyBookingModel>
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


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
}

