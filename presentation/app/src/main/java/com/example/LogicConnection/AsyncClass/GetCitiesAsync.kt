package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import java.lang.Exception

class GetCitiesAsync(activity: Activity) : MyGeneralAsyncTask(activity) {

    override fun doInBackground(vararg params: String?): String? {
        var result : String? = null
        try {
            result = ConnectionHandler.sendGet(
                BASEURL + "/cities",
                "",
                Token
            )
        } catch (e : Exception){
            error = e
            Log.d("server_connect",  "Exception: " + e.message)
        }
        return result
    }
}