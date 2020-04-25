package com.example.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyCityModel (
    @SerializedName("cityId")
    @Expose
    var cityName: String?,

    var centerList : ArrayList<MyCenterModel> = ArrayList<MyCenterModel>()
): MyDataModel() {
}