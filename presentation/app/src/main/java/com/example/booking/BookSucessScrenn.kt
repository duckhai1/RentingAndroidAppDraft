package com.example.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book2play.BookingHistoryScreen
import com.example.book2play.R
import kotlinx.android.synthetic.main.book_sucess_screen.*

class BookSucessScrenn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_sucess_screen)

        booking_list_button.setOnClickListener {
            val intent = Intent(this, BookingHistoryScreen::class.java)
            startActivity(intent)
        }
    }
}
