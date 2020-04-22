package com.example.LogicConnection.Handler

import android.app.Activity
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.LogicConnection.AsyncClass.createBookingAsync
import com.example.LogicConnection.Type.MyBookingModel

class ApiHandler {
    companion object{

        fun createBooking(myBookingModel: MyBookingModel, activity: Activity){
            val date = myBookingModel.date
            val start = myBookingModel.start
            val end = myBookingModel.end
            val city = myBookingModel.city
            val center = myBookingModel.center
            val court = myBookingModel.court
            val player = myBookingModel.player
            createBookingAsync(activity).execute(date, start, end, city, center, court, player)
        }
    }
}