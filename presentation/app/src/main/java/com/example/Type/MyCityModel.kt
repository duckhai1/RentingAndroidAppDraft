package com.example.Type

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyCityModel (
    @SerializedName("cityId")
    @Expose
    var cityName: String?,
    @SerializedName("Latitude")
    @Expose
    var latitude: Double,
    @SerializedName("Longitude")
    @Expose
    var longitude: Double,

    var location: LatLng = LatLng(latitude, longitude)
): MyDataModel() {
}