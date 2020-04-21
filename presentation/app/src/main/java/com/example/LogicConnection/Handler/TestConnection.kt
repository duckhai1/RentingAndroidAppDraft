package com.example.LogicConnection.Handler

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.LogicConnection.Type.MyBookingModel
import com.example.book2play.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_test_connection.*
import java.sql.Timestamp

class TestConnection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_connection)

        submit_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_SHORT).show()
                RequestAsync().execute()
//                val newBooking = MyBookingModel(
//                    "2020-09-23",
//                    "08:00:00",
//                    "09:00:00",
//                    "city1",
//                    "center1",
//                    "court1",
//                    "player1"
//                )
//                ApiHandler.createBooking(newBooking)
            }

        })
    }

    inner class RequestAsync : AsyncTask<String?, String?, String?>(){
        override fun doInBackground(vararg params: String?): String? {
            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
            return try {
//                //GET Request
//                return ConnectionHandler.sendGet("http://10.0.2.2:8000/api/bookings?bookingId=booking1");

                // POST Request
                val newBooking = MyBookingModel(
                    "2020-09-23",
                    "08:00:00 AM",
                    "09:00:00 AM",
                    "city1",
                    "center1",
                    "court1",
                    "player1"
                )
                val postJson = gson.toJson(newBooking)
                Log.d("java_connection", "postJson $postJson")
                val postDataParams = gson.fromJson(postJson, JsonObject::class.java)
//                postDataParams.addProperty("bookingId", "booking5")
//                postDataParams.addProperty("createAt", Timestamp.valueOf("2007-09-23 10:10:10.0"))
                ConnectionHandler.sendPost(
                    "http://10.0.2.2:8000/api/bookings",
                    postDataParams
                )
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
