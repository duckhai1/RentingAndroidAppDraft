package com.example.LogicConnection.Handler

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.AsyncClass.*
import com.example.Type.*

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

        fun getSportCenterBookings(activity: Activity, centerModel: MyCenterModel, date: String) : String?{
            val centerName = centerModel.centerName
            val cityName = centerModel.cityName
            Log.d("server_connect", "try to get booking in specific sportcenter on server")
            val response = getSportCenterBookingsAsync(activity).execute(centerName, cityName, date).get()

            return response
        }

        fun getPlayerBookings(activity: Activity, playerModel: MyPlayerModel) : String?{
            val playerName = playerModel.playerName
            Log.d("server_connect", "try to get booking in specific player on server")
            val response = getPlayerBookingsAsync(activity).execute(playerName).get()

            return response
        }

        fun getPlayerBookingsInCity(activity: Activity, playerModel: MyPlayerModel, cityModel: MyCityModel, date:String) : String?{
            val playerName = playerModel.playerName
            val cityName = cityModel.cityName
            Log.d("server_connect", "try to get booking in specific player in city on server")
            val response = getPlayerBookingsInCityAsync(activity).execute(playerName, cityName, date).get()

            return response
        }

    }
}