package com.example.LogicConnection.Handler

import com.example.LogicConnection.AsyncClass.createBookingAsync
import com.example.LogicConnection.Type.MyBookingModel

class ApiHandler {
    companion object{

        fun createBooking(myBookingModel: MyBookingModel){
            val id = myBookingModel.id
            val date = myBookingModel.date
            val start = myBookingModel.start
            val end = myBookingModel.end
            val city = myBookingModel.city
            val center = myBookingModel.center
            val court = myBookingModel.court
            val player = myBookingModel.player
            createBookingAsync().execute(date, start, end, city, center, court, player, id)
        }
    }
}