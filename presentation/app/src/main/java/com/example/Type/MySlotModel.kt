package com.example.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MySlotModel(
    @SerializedName("startTime")
    @Expose
    var startTime : String,
    @SerializedName("endTime")
    @Expose
    var endTime : String,
    @SerializedName("court")
    @Expose
    var court : String
) : MyDataModel() {
}