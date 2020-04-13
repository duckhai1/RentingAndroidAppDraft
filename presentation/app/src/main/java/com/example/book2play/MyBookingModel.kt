package com.example.book2play

import java.io.Serializable

class MyBookingModel(
    var date : String = "",     // date of booking
    var week: String = "",      // date of the week of the booking
    var time: String = "",      // time of the booking
    var city: String = "",      // booking city
    var center: String= "",     // booking sport center
    var court : String = "",    // booking court
    var player: String = "",    // booking player name
    var status: Int = 0         // status 0: unpaid; 1:paid
) :Serializable {
    override fun toString(): String {
        return "Date: " +date + "\nWeek: " + week + "\nTime: "+time +
                "\nCity: "+city+"\nCenter: " +center+"\nCourt: "+ court+"\nPlayer: "+player+
                "\nStatus: " + if(status == 0) "Unpaid" else "Paid"
    }
}