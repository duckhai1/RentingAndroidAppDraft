package com.example.CreateBooking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.SelectTime.SelectTimeScreen

import com.example.LogicConnection.Type.MyBookingModel
import com.example.book2play.R
import java.text.SimpleDateFormat
import java.util.*

class SearchDayScreen : AppCompatActivity() {
    var calendarView: CalendarView? = null
    var bt: Button? = null
    var date: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchday)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("make booking", "SearchDayScreen: " +bookingInfo.toString())


        val toolbar =
            findViewById<View>(R.id.toolbarSD) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Pick your date!"

        val textView: TextView = findViewById(R.id.centerName)
        textView.setText(intent.getStringExtra("CENTERNAME"))

        val ctnBtn =
            findViewById<ImageView>(R.id.continueButton)
        calendarView = findViewById<View>(R.id.calendarView) as CalendarView
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date(calendarView!!.date))
        calendarView!!.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            date = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
            val display_month = if (month < 9) "0"+(month+1) else (month+1)
            date = "" + year + "-" +  display_month + "-" + dayOfMonth.toString()
        }
//        if (date == null) {
//            Log.d("calendar", currentDate)
//        } else {
//            Log.d("calendar", date)
//        }
        ctnBtn.setOnClickListener {
            val intent = Intent(applicationContext, SelectTimeScreen::class.java)
            val bookingDay = if (date == null) currentDate else date
            Log.d("calendar", bookingDay)
            // update bookingInfo
            if (bookingInfo != null && bookingDay != null) {
                Log.d("calendar", "Everything okay")
                bookingInfo.date = bookingDay
            }
            else {
                val bookingInfo = bookingDay?.let { it1 ->
                    MyBookingModel(
                        date = it1
                    )
                }
            }
            intent.putExtra("BookingInfo", bookingInfo)
            startActivity(intent)
        }
    }
}