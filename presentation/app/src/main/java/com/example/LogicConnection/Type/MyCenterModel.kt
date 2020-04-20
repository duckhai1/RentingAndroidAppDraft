package com.example.LogicConnection.Type

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MyCenterModel(
    @SerializedName("sportCenterId")
    var centerName : String,
    @SerializedName("city")
    var city : String = "",
    var courtList : ArrayList<MyCourtModel> = ArrayList<MyCourtModel>()
) : MyDataModel{
}