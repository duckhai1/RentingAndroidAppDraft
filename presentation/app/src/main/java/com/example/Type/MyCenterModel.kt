package com.example.Type

import com.example.book2play.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyCenterModel(
    @SerializedName("sportCenterId")
    @Expose
    var centerName : String?,
    @SerializedName("cityId")
    @Expose
    var cityName : String?,

    var address : String = "",                      // center address
    var avatar: Int = R.drawable.sancaulong,        // center image
    var courtList : ArrayList<MyCourtModel> = ArrayList<MyCourtModel>()
) : MyDataModel{
}