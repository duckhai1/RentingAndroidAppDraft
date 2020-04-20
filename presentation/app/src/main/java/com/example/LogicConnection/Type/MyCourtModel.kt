package com.example.LogicConnection.Type

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MyCourtModel (
    @SerializedName("courtId")
    var courtName : String,
    @SerializedName("sportCenter")
    var center : String = "",
    var slotList : ArrayList<MySlotModel> = ArrayList<MySlotModel>()
): MyDataModel {
}