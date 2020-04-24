package com.example.LogicConnection.AsyncClass

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.widget.Toast
import com.example.Type.MyBookingModel

abstract class MyGeneralAsyncTask (activity: Activity) : AsyncTask<String?, String?, String?>(){
    val myTask = this
    val activity = activity
    lateinit var newBooking : MyBookingModel
    var error : Exception? = null
    val dialog = ProgressDialog(activity)

    override fun onPreExecute() {
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
        // if connect successful
        if (error == null) {
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

    abstract fun jobSuccess()
    abstract fun jobFail()
}