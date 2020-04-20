package com.example.LogicConnection.Type

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MyCityModel (
    @SerializedName("cityId")
    var CityName: String,
    var centerList : ArrayList<MyCenterModel> = ArrayList<MyCenterModel>()
): MyDataModel {
}