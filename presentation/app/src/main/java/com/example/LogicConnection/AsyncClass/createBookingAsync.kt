package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.CreateBooking.BookSucessScrenn
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.LogicConnection.Type.MyBookingModel
import com.example.book2play.MainActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_court1.*
import java.io.IOException


class createBookingAsync(activity: Activity) : AsyncTask<String?, String?, String?>(){
    var error : Exception? = null
    val activity = activity
    lateinit var newBooking : MyBookingModel

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
        val postJson = gson.toJson(newBooking)
        val postDataParams = gson.fromJson(postJson, JsonObject::class.java)

        try {
            result = ConnectionHandler.sendPost(
                "http://10.0.2.2:8000/api/bookings",
                postDataParams
            )
        } catch (e: Exception) {
            error = e
            result = "Exception: " + e.message
        }
        return result
    }
    override fun onPostExecute(s: String?) {
        Log.d("server_connect", s)
        activity.llProgressBar.visibility = View.GONE
        if (error == null) {
            val intent = Intent(activity, BookSucessScrenn::class.java)
            intent.putExtra("BookingInfo", newBooking)
            this.activity.startActivity(intent)
        }
        // can not connect to server
        else {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Connection error")
            builder.setMessage("Can not connect to the server. Please check you internet connection")
            builder.setNeutralButton("OK"){dialog, which ->
                val home_intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(home_intent)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
    }

}