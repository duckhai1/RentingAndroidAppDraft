package com.example.LogicConnection.Handler

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.LogicConnection.AsyncClass.*
import com.example.Type.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection

class ApiHandler {
    companion object{

        fun createBooking(activity: Activity, myBookingModel: MyBookingModel){
            val date = myBookingModel.date
            val start = myBookingModel.start
            val end = myBookingModel.end
            val city = myBookingModel.city
            val center = myBookingModel.center
            val court = myBookingModel.court
            Log.d("server_connect", "try to create booking on server")
            CreateBookingAsync(activity).execute(date, start, end, city, center, court)
        }

        fun getBookingInCourt(activity: Activity, courtName : String, centerName: String, cityName: String, date: String) : ArrayList<MyBookingModel>{
            Log.d("server_connect", "try to get booking in specific court on server")
            val response = GetBookingInCourtAsync(activity).execute(courtName, centerName, cityName, date).get()
            return decodeToList<MyBookingModel>(response)
        }

        fun getBookingInSportCenter(activity: Activity, centerName: String, cityName: String, date: String) : ArrayList<MyBookingModel>{
            Log.d("server_connect", "try to get booking in specific sportcenter on server")
            val response = GetBookingInSportCenterAsync(activity).execute(centerName, cityName, date).get()
            return decodeToList<MyBookingModel>(response)
        }

        fun getBookingOfPlayer(activity: Activity) : ArrayList<MyBookingModel>{
            Log.d("server_connect", "try to get booking in specific player on server")
            val response = GetBookingOfPlayerAsync(activity).execute().get()
            return decodeToList<MyBookingModel>(response)
        }

        fun getBookingOfPlayerInCity(activity: Activity, cityName : String, date:String) : ArrayList<MyBookingModel>{
            Log.d("server_connect", "try to get booking in specific player in city on server")
            val response = GetBookingOfPlayerInCityAsync(activity).execute(cityName, date).get()
            return decodeToList<MyBookingModel>(response)
        }

        fun getCityList(activity: Activity) : ArrayList<MyCityModel>{
            val response = GetCitiesAsync(activity).execute().get()
            return decodeToList<MyCityModel>(response)
        }

        fun getSportCenterInCity(activity: Activity, cityName: String) : ArrayList<MyCenterModel>{
            val response = GetSportCenterInCityAsync(activity).execute(cityName).get()
            return decodeToList<MyCenterModel>(response)
        }

        fun getCourtInSportCenter(activity: Activity, centerName: String, cityName: String) : ArrayList<MyCourtModel>{
            val response = GetCourtInSportCenterAsync(activity).execute(centerName, cityName).get()
            return decodeToList<MyCourtModel>(response)
        }

        fun getSlotInCourt(activity: Activity, courtName: String,centerName: String, cityName: String, date: String): ArrayList<MySlotModel>{
            Log.d("server_connect", "try to get slot for court on server")
            val response = GetSlotInCourtAsync(activity).execute(courtName,centerName, cityName, date).get()
            return decodeToList<MySlotModel>(response)
        }

        fun cancelBooking(activity: Activity, bookingId: String) : Boolean{
            val status = CancelBookingAsync(activity).execute(bookingId).get()
            if (status == HttpURLConnection.HTTP_OK.toString()){
                return true
            }
            return false
        }



        private inline fun <reified T : MyDataModel> decodeToList(jsonString: String?) : ArrayList<T>{
            val ListType = TypeToken.getParameterized(ArrayList::class.java, T::class.java)
            val result =  Gson().fromJson<ArrayList<T>>(jsonString, ListType.type)
            if (result == null){
                return ArrayList<T>()
            }
            return result
        }

    }
}