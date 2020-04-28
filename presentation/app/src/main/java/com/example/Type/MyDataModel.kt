package com.example.Type

import com.google.gson.Gson
import java.io.Serializable

abstract class MyDataModel :Serializable{
    override fun toString(): String {
        return Gson().toJson(this)
    }
}