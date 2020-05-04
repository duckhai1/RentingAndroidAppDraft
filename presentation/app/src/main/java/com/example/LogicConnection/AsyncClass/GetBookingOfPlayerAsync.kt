package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import com.google.gson.JsonObject
import java.lang.Exception

class GetBookingOfPlayerAsync(activity: Activity) : MyGeneralAsyncTask(activity) {

    override fun doInBackground(vararg params: String?): String? {
        var result : String? = null
        val request = JsonObject()
        val requestData = ConnectionHandler.encodeParams(request)
        try {
            result = ConnectionHandler.sendGet(
                BASEURL + "/bookings",
                requestData,
                Token
            )
        } catch (e : Exception){
            error = e
            Log.d("server_connect",  "Exception: " + e.message)
        }
        return result
    }

}