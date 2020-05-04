package com.example.Type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

class MyBookingModel(
    @SerializedName("bookingDate")
    @Expose
    var date : String? = "",     // date of booking
    @SerializedName("bookingStartTime")
    @Expose
    var start: String? = "",     // start time of the booking
    @SerializedName("bookingEndTime")
    @Expose
    var end: String? = "",       // end time of the booking
    @SerializedName("cityId")
    @Expose
    var city: String? = "",      // booking city
    @SerializedName("sportCenterId")
    @Expose
    var center: String?= "",     // booking sport center
    @SerializedName("courtId")
    @Expose
    var court : String? = "",    // booking court
    @SerializedName("playerId")
    @Expose(serialize = false)
    var player: String?= "",    // booking player name
    @SerializedName("isPaid")
    @Expose(serialize = false)
    var isPaid: Boolean = false,        // status 0: unpaid; 1:paid
    @SerializedName("bookingId")
    @Expose(serialize = false)
    var id : String? ="",       // bookingId


    var week: String  = "",      // date of the week of the booking
    var time: String = ""

) :MyDataModel() {
    fun getWeekDay(): String {
        return if (date == "") "" else SimpleDateFormat("EE").format(SimpleDateFormat("yyyy-MM-dd").parse(date))
    }
    fun getHourTime():String{
        return if (start == "" || end == "") "" else
            SimpleDateFormat("HH:mm").format(SimpleDateFormat("HH:mm:ss").parse(start)) + "-" +
                    SimpleDateFormat("HH:mm").format(SimpleDateFormat("HH:mm:ss").parse(end))
    }
}

