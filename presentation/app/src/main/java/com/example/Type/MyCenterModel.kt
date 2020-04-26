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

    var address : String = "I don't know, they don't give me the address",                      // center address
    var avatar: Int = R.drawable.hcmc1,             // center image
    var courtList : ArrayList<MyCourtModel> = ArrayList<MyCourtModel>()
) : MyDataModel() {
}