package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.example.Type.MyBookingModel
import com.example.book2play.MyApplication
import com.example.book2play.ScreenView.Activity.MainActivity
import java.net.HttpURLConnection

abstract class MyGeneralAsyncTask (activity: Activity) : AsyncTask<String?, String?, String?>(){
    val BASEURL = "http://10.0.2.2:8000/api"
    lateinit var Token :String
    lateinit var TokenType :String
    var isBadRequest : Boolean = false

    val myTask = this
    val activity = activity
    lateinit var newBooking : MyBookingModel
    var error : Exception? = null
    val dialog = ProgressDialog(activity)

    override fun onPreExecute() {
        Token = (activity.application as MyApplication).getToken()
        TokenType = (activity.application as MyApplication).getTokenType()
        // make processing dialog
        dialog.setMessage("Trying to connecting server")
        dialog.setCancelable(true)
        dialog.setOnCancelListener(DialogInterface.OnCancelListener {
            myTask.cancel(true)
            Toast.makeText(activity,"Cancel booking process", Toast.LENGTH_LONG).show();
        })
        dialog.show()
        super.onPreExecute()
    }

    override fun onPostExecute(result: String?) {
        if (dialog.isShowing) {
            dialog.dismiss();
        }

        // Bad request to server
        if (result == HttpURLConnection.HTTP_BAD_REQUEST.toString()){
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

    open fun jobSuccess() {}
    open fun jobFail() {
        val home_intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(home_intent)
    }
    open fun jobUnrecognized(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Invalid action")
        builder.setMessage("This action can not be done. Please choose another action")
        builder.setPositiveButton("OK"){dialog, which ->
            // do nothing
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}