package com.example.LogicConnection.Handler

import android.util.Base64
import android.util.Log
import com.example.Type.MyDataModel
import com.example.book2play.MyApplication
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
        fun sendGet(url: String?, postDataParams: String, token : String, tokenType: String): String {
            // handle request
            val fullUrl = url+"?"+postDataParams
            Log.d("server_connect", "GET url: "+fullUrl)

            // setup connection
            val conn = setupConnection(fullUrl, "GET", token, tokenType)

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
                HttpURLConnection.HTTP_BAD_REQUEST.toString()
            }
        }

        // POST method request
        fun sendPost(url: String?, requestData: String, token: String, tokenType: String): String? {
            // setup connection
            val conn = setupConnection(url, "POST", token, tokenType)

            // handle request
            val os = conn.outputStream
            val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            Log.d("server_connect", "requestJson: $requestData")
            writer.write(requestData)
            writer.flush()
            writer.close()
            os.close()

            // handle response
            val responseCode = conn.responseCode
            // check if request success
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                Log.d("server_connect", "Successful connect")
                return HttpURLConnection.HTTP_CREATED.toString()
            }
            return HttpURLConnection.HTTP_BAD_REQUEST.toString()
        }

        // PUT method request
        fun sendPut(url: String?, requestData: String, token: String, tokenType: String): String? {
            // setup connection
            val conn = setupConnection(url, "PUT", token, tokenType)

            // handle request
            val os = conn.outputStream
            val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            Log.d("server_connect", "requestJson: $requestData")
            writer.write(requestData)
            writer.flush()
            writer.close()
            os.close()

            // handle response
            val responseCode = conn.responseCode
            // check if request success
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
                HttpURLConnection.HTTP_BAD_REQUEST.toString()
            }
        }

        // DELETE method request
        fun sendDelete(url: String?, postDataParams: String, token : String, tokenType: String): String {
            // handle request
            val fullUrl = url+"?"+postDataParams
            Log.d("server_connect", "GET url: "+fullUrl)

            // setup connection
            val conn = setupConnection(fullUrl, "DELETE", token, tokenType)

            // handle response
            val responseCode = conn.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
                Log.d("server_connect", "Successful connect")
                HttpURLConnection.HTTP_OK.toString()
            } else {
                HttpURLConnection.HTTP_BAD_REQUEST.toString()
            }
        }


        fun checkTokenStatus(url: String?, token: String, tokenType: String) : String?{
            val conn = setupConnection(url, "GET", token, tokenType)
            return conn.responseCode.toString()
        }

        // convert jsonObject to query in url
        fun encodeParams(jsonObject : JsonObject): String {
            val result = StringBuilder()
            var first = true
            val itr: Iterator<String> = jsonObject.keySet().iterator()
            while (itr.hasNext()) {
                val key = itr.next()
                val value = jsonObject[key]
                if (first) first = false else result.append("&")
                result.append(key+"="+value.asString)
            }
            return result.toString()
        }

        private fun setupConnection(url: String?, method: String, token: String, tokenType: String) : HttpURLConnection{
            val url = URL(url)
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 10000
            conn.connectTimeout = 10000
            conn.requestMethod = method
            conn.setRequestProperty("Token", token)
            conn.setRequestProperty("TokenType", tokenType)
            Log.d("Token ", "Token in connection: " +token)
            Log.d("Token ", "TokenType in connection: " +tokenType)
            if (method == "GET" || method == "PUT" || method == "DELETE"){
                conn.doInput = true
            } else if (method == "POST" || method == "PUT") {
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
    }
}