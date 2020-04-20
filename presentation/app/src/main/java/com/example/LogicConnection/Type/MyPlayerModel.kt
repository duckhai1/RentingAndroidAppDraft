package com.example.LogicConnection.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MyPlayerModel (
    @SerializedName("playerId")
    @Expose

    var playerId : String,
    var password : String,
    var playerName: String = "",
    var token : String = ""
) : MyDataModel {
}