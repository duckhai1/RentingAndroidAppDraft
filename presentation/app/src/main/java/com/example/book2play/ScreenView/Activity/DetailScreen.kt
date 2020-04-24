package com.example.book2play.ScreenView.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Type.MyBookingModel
import com.example.book2play.Adapter.DetailAdapter
import com.example.book2play.R
import kotlinx.android.synthetic.main.screen_detail.*


class DetailScreen : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_detail)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("booking list", "Booking Detail: " +bookingInfo.toString())

        val toolbar = findViewById<View>(R.id.toolbarLS2) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Booking Detail"


        val detailList = setupDetailList(bookingInfo)
        val descriptionList = setupDescriptionList()

        listDetail.layoutManager =
            CustomLinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        listDetail.addItemDecoration(
            DividerItemDecoration(listDetail.getContext(),DividerItemDecoration.VERTICAL)
        )
        listDetail.isNestedScrollingEnabled = false
        listDetail.adapter = DetailAdapter(
            detailList,
            descriptionList,
            this
        )
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    class CustomLinearLayoutManager(context: Context?,orientation: Int,reverseLayout: Boolean
    ) :LinearLayoutManager(context, orientation, reverseLayout) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    fun setupDetailList(bookingInfo : MyBookingModel?): ArrayList<String>{
        val detailList = ArrayList<String>()
        if (bookingInfo != null) {
            detailList.add(bookingInfo.date + " " + bookingInfo.week + ": " + bookingInfo.time)
            detailList.add(bookingInfo.center + ", " + bookingInfo.city)
            detailList.add(bookingInfo.court.toString())
            detailList.add(if (bookingInfo.status == 0) "Unpaid" else "Paid")
        }
        return detailList
    }

    fun setupDescriptionList() : ArrayList<String>{
        val descriptionList = ArrayList<String>()
        descriptionList.add("Booking Time")
        descriptionList.add("Location")
        descriptionList.add("Court")
        descriptionList.add("Booking Confirmation")
        return descriptionList
    }
}