package com.example.locationsscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.book2play.R
import com.example.booking.SelectTimeScreen
import java.text.SimpleDateFormat
import java.util.*

class SearchDayScreen : AppCompatActivity() {
    var calendarView: CalendarView? = null
    var tv: TextView? = null
    var bt: Button? = null
    var date: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchday)
        val toolbar =
            findViewById<View>(R.id.toolbarSD) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Pick your date!"
        tv = findViewById<View>(R.id.centerName) as TextView
        tv!!.text = intent.getStringExtra("CENTERNAME")
        val ctnBtn =
            findViewById<ImageView>(R.id.continueButton)
        calendarView = findViewById<View>(R.id.calendarView) as CalendarView
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date(calendarView!!.date))
        calendarView!!.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
        }
        ctnBtn.setOnClickListener {
            val intent = Intent(
                applicationContext,
                SelectTimeScreen::class.java
            )
            intent.putExtra("THEDATE", if (date == null) currentDate else date)
            startActivity(intent)
        }
    }
}