package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import com.google.gson.JsonObject
import java.lang.Exception

class GetBookingInSportCenterAsync(activity: Activity) : MyGeneralAsyncTask(activity) {

    override fun doInBackground(vararg params: String?): String? {
        var result : String? = null
        val request = JsonObject()
        request.addProperty("sportCenterId", params[0])
        request.addProperty("cityId", params[1])
        request.addProperty("date", params[2])
        val requestData = ConnectionHandler.encodeParams(request)
        try {
            result = ConnectionHandler.sendGet(
                BASEURL + "/bookings",
                requestData,
                Token,
                TokenType
            )
        } catch (e : Exception){
            error = e
            Log.d("server_connect",  "Exception: " + e.message)
        }
        return result
    }

}