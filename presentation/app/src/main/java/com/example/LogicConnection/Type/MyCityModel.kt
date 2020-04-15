package com.example.LogicConnection.Type

import java.io.Serializable

class MyCityModel (
    var CityName: String,
    var centerList : ArrayList<MyCenterModel> = ArrayList<MyCenterModel>()
): MyDataModel {
}