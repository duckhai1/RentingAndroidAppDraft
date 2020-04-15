package com.example.LogicConnection.Handler

import android.util.Log
import com.example.LogicConnection.Type.MyBookingModel
import com.example.LogicConnection.Type.MyDataModel
import com.google.gson.Gson
import java.io.FileOutputStream
import java.io.FileWriter
import java.lang.Exception

class ConnectionHandler {
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