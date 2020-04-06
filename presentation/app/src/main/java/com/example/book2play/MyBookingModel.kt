package com.example.book2play

import java.io.Serializable

class MyBookingModel(val date : String, val week: String, val title: String, val info: String, val status: Int = 0) :Serializable {
    // status 0: unpaid; 1:paid
}