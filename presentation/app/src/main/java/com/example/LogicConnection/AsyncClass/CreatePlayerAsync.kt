package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.widget.Toast
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

    override fun onPostExecute(result: String?) {
        if (dialog.isShowing) {
            dialog.dismiss();
        }

        // if player already booked
        if (Integer.parseInt(result.toString()) == 405){
            Toast.makeText(activity, "Welcome back book2player", Toast.LENGTH_SHORT).show()
        }
        // Bad request to server
        if (Integer.parseInt(result.toString()) in 400..500){
            jobUnrecognized()
        }
        // if connect successful
        else if (error == null) {
            jobSuccess()
        }
        // can not connect to server
        else {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Connection error")
            builder.setMessage("Can not connect to the server. Please check you internet connection")
            builder.setPositiveButton("OK"){dialog, which ->
                jobFail()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
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