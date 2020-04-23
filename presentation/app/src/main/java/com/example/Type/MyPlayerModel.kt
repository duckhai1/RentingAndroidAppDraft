package com.example.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyPlayerModel (
    @SerializedName("playerId")
    @Expose
    var playerId : String,

    var password : String,
    var playerName: String = "",
    var token : String = ""
) : MyDataModel {
}