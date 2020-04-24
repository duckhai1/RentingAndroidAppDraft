package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.book2play.BookSucessScreen
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.Type.MyBookingModel
import com.example.book2play.MainActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class createBookingAsync(activity: Activity) : AsyncTask<String?, String?, String?>(){
    val myTask = this
    val activity = activity
    lateinit var newBooking : MyBookingModel
    var error : Exception? = null
    val dialog = ProgressDialog(activity)

    override fun onPreExecute() {
        // make processing dialog
        dialog.setMessage("Trying to connecting server")
        dialog.setCancelable(true)
        dialog.setOnCancelListener(DialogInterface.OnCancelListener {
            myTask.cancel(true)
            Toast.makeText(activity,"AsyncTask is stopped",Toast.LENGTH_LONG).show();
        })
        dialog.show()
        super.onPreExecute()
    }

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
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        // if connect successful
        if (error == null) {
            val intent = Intent(activity, BookSucessScreen::class.java)
            intent.putExtra("BookingInfo", newBooking)
            this.activity.startActivity(intent)
        }
        // can not connect to server
        else {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Connection error")
            builder.setMessage("Can not connect to the server. Please check you internet connection")
            builder.setPositiveButton("OK"){dialog, which ->
                val home_intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(home_intent)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
    }

}