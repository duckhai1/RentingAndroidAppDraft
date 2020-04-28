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


class CreateBookingAsync(activity: Activity) : MyGeneralAsyncTask(activity) {

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
                BASEURL + "/bookings",
                requestJson,
                Token
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

}