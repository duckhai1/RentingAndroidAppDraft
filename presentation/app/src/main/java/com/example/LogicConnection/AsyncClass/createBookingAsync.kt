package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.book2play.ScreenView.Activity.BookSucessScreen
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.Type.MyBookingModel
import com.example.book2play.ScreenView.Activity.MainActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class createBookingAsync(activity: Activity) : MyGeneralAsyncTask(activity) {

    override fun doInBackground(vararg params: String?): String? {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        var result : String? = null

        newBooking = MyBookingModel(
            params[0],
            params[1],
            params[2],
            params[3],
            params[4],
            params[5],
            params[6]
        )
        val requestJson = gson.toJson(newBooking)

        try {
            result = ConnectionHandler.sendPost(
                "http://10.0.2.2:8000/api/bookings",
                requestJson
            )
        } catch (e: Exception) {
            error = e
            Log.d("server_connect",  "Exception: " + e.message)
        }
        return result
    }

    override fun jobSuccess() {
        val intent = Intent(activity, BookSucessScreen::class.java)
        intent.putExtra("BookingInfo", newBooking)
        this.activity.startActivity(intent)
    }

    override fun jobFail() {
        val home_intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(home_intent)
    }

}