package com.example.LogicConnection.AsyncClass

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.LogicConnection.Type.MyBookingModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class createBookingAsync : AsyncTask<String?, String?, String?>(){
    override fun doInBackground(vararg params: String?): String? {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        var result : String? = null

        val newBooking = MyBookingModel(
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
            ConnectionHandler.sendPost(
                "http://10.0.2.2:8000/api/bookings",
                postDataParams
            )
        } catch (e: Exception) {
            result = "Exception: " + e.message
        }
        return result
    }
    override fun onPostExecute(s: String?) {
        if (s != null) {
//            Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show()
        }
    }

}