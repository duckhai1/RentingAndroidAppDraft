package com.example.LogicConnection.Type

import java.io.Serializable

class MyPlayerModel (
    var playerId : String,
    var password : String,
    var playerName: String = "",
    var token : String = ""
) : MyDataModel {
}