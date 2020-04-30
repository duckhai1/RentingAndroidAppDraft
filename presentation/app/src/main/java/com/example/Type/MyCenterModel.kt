package com.example.Type

import com.example.book2play.R
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyCenterModel(
    @SerializedName("sportCenterId")
    @Expose
    var centerName : String?,
    @SerializedName("cityId")
    @Expose
    var cityName : String?,
    @SerializedName("Latitude")
    @Expose
    var latitude: Double,
    @SerializedName("Longitude")
    @Expose
    var longitude: Double,
    @SerializedName("sportCenterAddress")
    @Expose
    var address : String = "I don't know, they don't give me the address",  // center address

    var location: LatLng = LatLng(latitude, longitude),
    var avatar: Int = R.drawable.hcmc1                                     // center image
) : MyDataModel() {
}