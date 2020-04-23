package com.example.LogicConnection.Handler

import android.util.Base64
import android.util.Log
import com.example.Type.MyDataModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ConnectionHandler {
    companion object{
        // GET method request
        fun sendGet(url: String?): String {
            // setup connection
            val conn = setupConnection(url, "GET")

            // handle response
            val responseCode = conn.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
                Log.d("server_connect", "Successful connect")
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
        fun sendPost(url: String?, postDataParams: JsonObject): String? {
            // setup connection
            val conn = setupConnection(url, "POST")

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
            // check if request success
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                Log.d("server_connect", "Successful connect")
                return "Create resource successful"
            }
            return "Fail to create resource"
        }

        private fun setupConnection(url: String?, method: String) : HttpURLConnection{
            val url = URL(url)
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 10000
            conn.connectTimeout = 10000
            conn.requestMethod = method
            if (method == "GET" || method == "DELETE"){
                conn.doInput = true
            } else {
                conn.doOutput = true
            }
            setupAuthorize(conn)
            return conn
        }

        private fun setupAuthorize(conn: HttpURLConnection){
            val username = "admin"
            val pass = "admin"
            // authentication
            val userPassword: String = username.toString() + ":" + pass
            val data: ByteArray = userPassword.toByteArray(Charsets.UTF_8)
            val encoding: String = Base64.encodeToString(data, Base64.DEFAULT)
            conn.setRequestProperty("Authorization", "Basic " + encoding)
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