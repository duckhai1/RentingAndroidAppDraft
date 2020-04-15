package com.example.LogicConnection.Type

import java.io.Serializable

class MyCourtModel (
    var courtName : String,
    var slotList : ArrayList<MySlotModel> = ArrayList<MySlotModel>()
): MyDataModel {
}