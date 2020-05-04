package com.example.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyCourtModel (
    @SerializedName("courtId")
    @Expose
    var courtName : String?,
    @SerializedName("sportCenterId")
    @Expose
    var centerName : String?,
    @SerializedName("cityId")
    @Expose
    var cityName : String?
): MyDataModel() {
}