package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.LogicConnection.Handler.ConnectionHandler
import com.example.book2play.ScreenView.Activity.LoginScreen
import com.example.book2play.ScreenView.Activity.SignUpScreen
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class SignupPlayerAsync(activity: Activity) : MyGeneralAsyncTask(activity) {
    override fun doInBackground(vararg params: String?): String? {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        var result : String? = null
        val playerInfo = JsonObject()
        playerInfo.addProperty("playerId", params[0])
        playerInfo.addProperty("password", params[1])
        val requestJson = gson.toJson(playerInfo)

        try {
            result = ConnectionHandler.sendPost(
                BASEURL + "/authen",
                requestJson,
                Token,
                TokenType
            )
        } catch (e: Exception) {
            error = e
            Log.d("login ",  "Exception: " + e.message)
        }
        return result
    }

    override fun jobSuccess() {
        Toast.makeText(activity, "Successful create new account. You can login now", Toast.LENGTH_LONG).show()
        val login_intent = Intent(activity, LoginScreen::class.java)
        activity.startActivity(login_intent)
    }

    override fun jobFail() {
        val signup_intent = Intent(activity, SignUpScreen::class.java)
        activity.startActivity(signup_intent)
    }

    override fun jobUnrecognized() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Invalid action")
        builder.setMessage("Can not create new account, please provide different username")
        builder.setPositiveButton("OK"){dialog, which ->
            // do nothing
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}