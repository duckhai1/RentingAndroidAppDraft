package com.example.LogicConnection.Type

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MySlotModel(
    @SerializedName("startTime")
    var startTime : String,
    @SerializedName("endTime")
    var endTime : String,
    @SerializedName("court")
    var court : String
) : MyDataModel {
}