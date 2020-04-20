package com.example.LogicConnection.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MyCenterModel(
    @SerializedName("sportCenterId")
    @Expose
    var centerName : String,
    @SerializedName("city")
    @Expose
    var city : String = "",

    var courtList : ArrayList<MyCourtModel> = ArrayList<MyCourtModel>()
) : MyDataModel{
}