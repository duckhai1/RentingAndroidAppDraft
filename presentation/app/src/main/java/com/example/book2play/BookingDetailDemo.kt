package com.example.book2play

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_booking_detail_demo.*

class BookingDetailDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_detail_demo)

        val bookingModel = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        if (bookingModel != null) {
            record_date.text = bookingModel.date
            record_info.text = bookingModel.info
            record_title.text = bookingModel.title
            record_weekday.text = bookingModel.week
            record_status.text = if (bookingModel.status == 0) "Unpaid" else "Paid"
        }
    }
}
