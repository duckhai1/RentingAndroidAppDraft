package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.book2play.ScreenView.Activity.LoginScreen
import java.lang.Exception

class CheckPlayerExistAsync(activity: Activity) : MyGeneralAsyncTask(activity)  {
    override fun doInBackground(vararg params: String?): String? {
        var result : String? = null
        Log.d("Token to check status: ", Token)
        try {
            result = ConnectionHandler.checkTokenStatus(
                BASEURL + "/players",
                Token,
                TokenType
            )
        } catch (e : Exception){
            error = e
            Log.d("server_connect",  "Exception: " + e.message)
        }
        return result
    }

    override fun jobFail() {
        val home_intent = Intent(activity, LoginScreen::class.java)
        activity.startActivity(home_intent)
    }

    override fun jobUnrecognized() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Can not verify user")
        builder.setMessage("This action can not be done. Please choose another action")
        builder.setPositiveButton("OK"){dialog, which ->
            jobFail()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}