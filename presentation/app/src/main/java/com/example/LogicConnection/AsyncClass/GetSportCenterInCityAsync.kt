package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import com.google.gson.JsonObject
import java.lang.Exception

class GetSportCenterInCityAsync(activity: Activity) : MyGeneralAsyncTask(activity) {
    override fun doInBackground(vararg params: String?): String? {
        var result : String? = null
        val request = JsonObject()
        request.addProperty("cityId", params[0])
        val requestData = ConnectionHandler.encodeParams(request)
        try {
            result = ConnectionHandler.sendGet(
                BASEURL + "/centers",
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