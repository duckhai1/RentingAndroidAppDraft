package com.example.book2play.ScreenView.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.Type.MyBookingModel
import com.example.book2play.R


import kotlinx.android.synthetic.main.screen_book_sucess.*

class BookSucessScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_book_sucess)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("make booking", "BookSuccessScreen: " +bookingInfo.toString())

        if (bookingInfo != null) {
            result_court.text = "Court: " + bookingInfo.court
            result_date.text = "Date: " + bookingInfo.date
            result_hour.text = "Period: " + bookingInfo.start + " - " +bookingInfo.end
            result_center.text = "Center: " + bookingInfo.center
            result_city.text = "City: " + bookingInfo.city
        }


        booking_list_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
