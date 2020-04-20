package com.example.LogicConnection.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

class MyBookingModel(
    @SerializedName("bookingDate")
    @Expose
    var date : String = "",     // date of booking
    @SerializedName("bookingStartTime")
    @Expose
    var start: String = "",     // start time of the booking
    @SerializedName("bookingEndTime")
    @Expose
    val end: String = "",       // end time of the booking
    @SerializedName("city")
    @Expose
    var city: String = "",      // booking city
    @SerializedName("center")
    @Expose
    var center: String= "",     // booking sport center
    @SerializedName("court")
    @Expose
    var court : String = "",    // booking court
    @SerializedName("player")
    @Expose
    var player: String = "",    // booking player name
    @SerializedName("isPaid")
    @Expose
    var status: Int = 0,        // status 0: unpaid; 1:paid

    var week: String  = SimpleDateFormat("EE").format(SimpleDateFormat("yyyy-MM-dd").parse(date)),      // date of the week of the booking
    var time: String = start + " " + end        // deprecated later

) :MyDataModel {


    override fun toString(): String {
        return "Date: " +date + "\nWeek: " + week + "\nTime: " + time +
                "\nCity: "+city+"\nCenter: " +center+"\nCourt: "+ court+"\nPlayer: "+player+
                "\nStatus: " + if(status == 0) "Unpaid" else "Paid"
    }

}

