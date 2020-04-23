package com.example.book2play.CreateBooking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.book2play.CreateBooking.SelectTime.ChooseTimeScreen

import com.example.Type.MyBookingModel
import com.example.book2play.R
import java.text.SimpleDateFormat
import java.util.*

class ChooseDayScreen : AppCompatActivity() {
    var calendarView: CalendarView? = null
    var date: String? = null
    var bookingInfo : MyBookingModel? = null
    var currentDate : String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_choose_day)

        // get last intent information
        bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        Log.d("make booking", "SearchDayScreen: " +bookingInfo.toString())


        val toolbar = findViewById<View>(R.id.toolbarSD) as Toolbar
        toolbar.setTitle("Pick your date!")
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        val textView: TextView = findViewById(R.id.centerName)
        textView.setText(intent.getStringExtra("CENTERNAME"))

        calendarView = findViewById<View>(R.id.calendarView) as CalendarView
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        currentDate = sdf.format(Date(calendarView!!.date))
        calendarView!!.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val display_month = if (month < 9) "0"+(month+1) else (month+1)
            date = "" + year + "-" +  display_month + "-" + dayOfMonth.toString()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.next_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_next ->{
                val intent = Intent(applicationContext, ChooseTimeScreen::class.java)
                val bookingDay = if (date == null) currentDate else date
                Log.d("calendar", bookingDay)
                // update bookingInfo
                if (bookingInfo != null && bookingDay != null) {
                    Log.d("calendar", "Everything okay")
                    bookingInfo!!.date = bookingDay
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
        return super.onOptionsItemSelected(item)
    }
}