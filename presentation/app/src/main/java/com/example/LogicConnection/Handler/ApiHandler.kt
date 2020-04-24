package com.example.LogicConnection.Handler

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.AsyncClass.createBookingAsync
import com.example.LogicConnection.AsyncClass.getCourtBookingsAsync
import com.example.Type.MyBookingModel
import com.example.Type.MyCourtModel

class ApiHandler {
    companion object{

        fun createBooking(activity: Activity, myBookingModel: MyBookingModel){
            val date = myBookingModel.date
            val start = myBookingModel.start
            val end = myBookingModel.end
            val city = myBookingModel.city
            val center = myBookingModel.center
            val court = myBookingModel.court
            val player = myBookingModel.player
            Log.d("server_connect", "try to create booking on server")
            createBookingAsync(activity).execute(date, start, end, city, center, court, player)
        }

        fun getCourtBookings(activity: Activity, courtModel: MyCourtModel, date: String) : String?{
            val courtName = courtModel.courtName
            val centerName = courtModel.centerName
            val cityName = courtModel.cityName
            Log.d("server_connect", "try to get booking in specific court on server")
            val response = getCourtBookingsAsync(activity).execute(courtName, centerName, cityName, date).get()

            return response
        }
    }
}