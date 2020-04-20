package com.example.LogicConnection.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MyCityModel (
    @SerializedName("cityId")
    @Expose
    var CityName: String,

    var centerList : ArrayList<MyCenterModel> = ArrayList<MyCenterModel>()
): MyDataModel {
}