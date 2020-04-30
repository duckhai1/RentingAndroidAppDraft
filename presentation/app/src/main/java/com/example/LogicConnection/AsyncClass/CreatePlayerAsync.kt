package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.book2play.ScreenView.Activity.LoginScreen
import java.lang.Exception

class CreatePlayerAsync(activity: Activity) : MyGeneralAsyncTask(activity) {
    override fun doInBackground(vararg params: String?): String? {
        var result : String? = null
        Log.d("Token to check status: ", Token)
        try {
            result = ConnectionHandler.sendPost(
                BASEURL + "/players",
                "{}",
                Token
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
    override fun jobUnrecognized(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Invalid login")
        builder.setMessage("Can not create new player associate with this account.\nPlease try another account")
        builder.setPositiveButton("OK"){dialog, which ->
            jobFail()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}