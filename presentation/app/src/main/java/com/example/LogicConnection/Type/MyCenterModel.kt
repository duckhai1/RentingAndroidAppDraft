package com.example.LogicConnection.Type

import java.io.Serializable

class MyCenterModel(
    var centerName : String,
    var courtList : ArrayList<MyCourtModel> = ArrayList<MyCourtModel>()
) : MyDataModel{
}