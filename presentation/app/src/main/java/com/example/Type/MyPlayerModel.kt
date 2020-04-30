package com.example.Type

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyPlayerModel (
    @SerializedName("playerId")
    @Expose
    var playerId : String,

    var playerName: String = "",
    var email: String = "",
    var image: String= "",
    var token : String = ""
) : MyDataModel() {

}