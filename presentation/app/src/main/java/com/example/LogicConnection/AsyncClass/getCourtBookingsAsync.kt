package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import com.google.gson.JsonObject
import java.lang.Exception


class getCourtBookingsAsync(activity: Activity) : MyGeneralAsyncTask(activity) {

    override fun doInBackground(vararg params: String?): String? {
        var result : String? = null
        val request = JsonObject()
        request.addProperty("courtId", params[0])
        request.addProperty("sportCenterId", params[1])
        request.addProperty("cityId", params[2])
        request.addProperty("date", params[3])
        val requestData = ConnectionHandler.encodeParams(request)
        try {
            result = ConnectionHandler.sendGet(
                "http://10.0.2.2:8000/api/bookings",
                requestData
            )
        } catch (e : Exception){
            error = e
            Log.d("server_connect",  "Exception: " + e.message)
        }
        return result
    }

    override fun jobSuccess() {}

    override fun jobFail() {}
}