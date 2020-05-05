package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.book2play.ScreenView.Activity.LoginScreen
import com.example.book2play.ScreenView.Activity.MainActivity
import com.example.book2play.ScreenView.Activity.SignUpScreen
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.lang.Exception

class LoginPlayerAsync(activity: Activity) : MyGeneralAsyncTask(activity) {
    override fun doInBackground(vararg params: String?): String? {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        var result : String? = null
        val playerInfo = JsonObject()
        playerInfo.addProperty("playerId", params[0])
        playerInfo.addProperty("password", params[1])
        val requestJson = gson.toJson(playerInfo)

        try {
            result = ConnectionHandler.sendPut(
                BASEURL + "/authen",
                requestJson,
                Token,
                TokenType
            )?.removeSurrounding("\"")
        } catch (e : Exception){
            error = e
            Log.d("server_connect",  "Exception: " + e.message)
        }
        return result
    }

    override fun jobSuccess() {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }


    override fun jobFail() {
        val login_intent = Intent(activity, LoginScreen::class.java)
        activity.startActivity(login_intent)
    }
    override fun jobUnrecognized() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Invalid action")
        builder.setMessage("Wrong password or username.\nIf you don't have account, click button below to create new account")
        builder.setPositiveButton("Signup"){dialog, which ->
            val signup_intent = Intent(activity, SignUpScreen::class.java)
            activity.startActivity(signup_intent)
        }
        builder.setNegativeButton("Try Again"){dialog, which ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}