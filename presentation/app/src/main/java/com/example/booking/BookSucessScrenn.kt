package com.example.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book2play.MainActivity
import com.example.book2play.MyBookingModel
import com.example.book2play.R
import kotlinx.android.synthetic.main.book_sucess_screen.*

class BookSucessScrenn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_sucess_screen)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel

        if (bookingInfo != null) {
            result_court.text = bookingInfo.court
            result_date.text = bookingInfo.date
            result_hour.text = "Period: " + bookingInfo.time
            result_center.text = bookingInfo.center
        }


        booking_list_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
