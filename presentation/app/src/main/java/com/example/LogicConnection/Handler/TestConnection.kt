package com.example.LogicConnection.Handler

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.book2play.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_test_connection.*

class TestConnection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_connection)

        submit_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(applicationContext, "Cliked", Toast.LENGTH_SHORT).show()
                RequestAsync().execute()
            }

        })
    }

    inner class RequestAsync : AsyncTask<String?, String?, String?>(){
        override fun doInBackground(vararg params: String?): String? {
            return try {
                //GET Request
                return ConnectionHandler.sendGet("http://10.0.2.2:8000/api/bookings");

//                // POST Request
//                val postDataParams = JsonObject()
//                postDataParams.addProperty("name", "Manjeet")
//                postDataParams.addProperty("id", "10")
//                ConnectionHandler.sendPost(
//                    "http://10.0.2.2:8000/api/bookings",
//                    postDataParams
//                )
            } catch (e: Exception) {
                "Exception: " + e.message
            }
        }
        override fun onPostExecute(s: String?) {
            if (s != null) {
                Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show()
            }
        }

    }
}
