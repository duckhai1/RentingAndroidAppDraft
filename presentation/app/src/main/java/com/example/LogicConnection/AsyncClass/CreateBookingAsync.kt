package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
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
            params[5]
        )
        val requestJson = gson.toJson(newBooking)

        try {
            result = ConnectionHandler.sendPost(
                BASEURL + "/bookings",
                requestJson,
                Token,
                TokenType
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

    override fun jobUnrecognized() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Invalid action")
        builder.setMessage("Some problems have occur when try to book selected slot. Please choose another time")
        builder.setPositiveButton("OK"){dialog, which ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}