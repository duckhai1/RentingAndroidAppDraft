package com.example.LogicConnection.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MyCourtModel (
    @SerializedName("courtId")
    @Expose
    var courtName : String,
    @SerializedName("sportCenter")
    @Expose
    var centerName : String = "",

    var slotList : ArrayList<MySlotModel> = ArrayList<MySlotModel>()
): MyDataModel {
}