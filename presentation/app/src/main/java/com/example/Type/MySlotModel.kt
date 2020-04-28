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
    @SerializedName("courtId")
    @Expose(deserialize = false)
    var courtName : String,
    @SerializedName("sportCenterId")
    @Expose(deserialize = false)
    var centerName : String,
    @SerializedName("cityId")
    @Expose(deserialize = false)
    var cictyName : String

) : MyDataModel() {
}