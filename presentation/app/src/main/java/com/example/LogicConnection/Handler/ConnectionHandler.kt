package com.example.LogicConnection.Handler

import android.util.Base64
import android.util.Log
import com.example.LogicConnection.Type.MyBookingModel
import com.example.LogicConnection.Type.MyDataModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class ConnectionHandler {
    companion object{
        // GET method request
        fun sendGet(url: String?): String {
            val obj = URL(url)
            val conn = obj.openConnection() as HttpURLConnection

            // authentication
            val username = "admin"
            val pass = "admin"
            val userPassword: String = username.toString() + ":" + pass
            val data: ByteArray = userPassword.toByteArray(Charsets.UTF_8)
            val encoding: String = Base64.encodeToString(data, Base64.DEFAULT)
            conn.setRequestProperty("Authorization", "Basic " + encoding)
            conn.requestMethod = "GET"

            val responseCode = conn.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
                Log.d("java_connection", "Successful connect")
                val input = BufferedReader(InputStreamReader(conn.inputStream))

                var inputLine: String?
                val response = StringBuffer()
                while (input.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                input.close()
                response.toString()
            } else {
                ""
            }
        }

        // POST method request
        fun sendPost(r_url: String?, postDataParams: JsonObject): String? {
            val url = URL(r_url)

            // setup connection
            val conn = url.openConnection() as HttpURLConnection
            // authentication
            val username = "admin"
            val pass = "admin"
            val userPassword: String = username.toString() + ":" + pass
            val data: ByteArray = userPassword.toByteArray(Charsets.UTF_8)
            val encoding: String = Base64.encodeToString(data, Base64.DEFAULT)
            conn.setRequestProperty("Authorization", "Basic " + encoding)
            conn.readTimeout = 20000
            conn.connectTimeout = 20000
            conn.requestMethod = "POST"
            conn.doInput = true
            conn.doOutput = true

            // handle request
            val os = conn.outputStream
            val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            val requestJson = postDataParams.toString()
            Log.d("server_connect", "requestJson: $postDataParams")
            writer.write(requestJson)
            writer.flush()
            writer.close()
            os.close()

            // handle response
            val responseCode = conn.responseCode
            Log.d("server_connect", "responseCode: $responseCode")
            // check if request success
            if (responseCode == HttpsURLConnection.HTTP_OK) {
//                Log.d("java_connection", "Successful connect")
//                val input = BufferedReader(InputStreamReader(conn.inputStream))
//                val response = StringBuffer("")
//                var line: String? = input.readLine()
//                while (line != null) {
//                    response.append(line)
//                    line = input.readLine()
//                }
//                input.close()
//                return response.toString()
                return "Success"
            }
            return "Unsuccess"
        }

        // convert jsonObject to query in url
        private fun encodeParams(params: JsonObject): String {
            val result = StringBuilder()
            var first = true
            val itr: Iterator<String> = params.keySet().iterator()
            while (itr.hasNext()) {
                val key = itr.next()
                val value: Any = params[key]
                if (first) first = false else result.append("&")
                result.append(URLEncoder.encode(key, "UTF-8"))
                result.append("=")
                result.append(URLEncoder.encode(value.toString(), "UTF-8"))
            }
            return result.toString()
        }
    }

    fun createJson(data : MyDataModel, fileName : String){
        val gson = Gson()
        var json : String = gson.toJson(data)


        try {
            var fo = FileWriter(fileName)
            fo.write(json)
            fo.close()
        } catch (ex: Exception){
            Log.d("File write", ex.message)
        }
    }

    // TODO get Json from logic tier
    fun loadJson(){
        val gson = Gson()
        val json : String = ""
        val data : MyDataModel
        data = gson.fromJson(json, MyDataModel::class.java)
    }
}