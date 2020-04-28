package com.example.book2play

import android.app.Application

class MyApplication : Application() {
    private var playerId : String = ""
    private var token : String = ""

    public fun getplayerId() : String {
        return playerId
    }

    public fun setplayerId(playerId: String){
        this.playerId = playerId
    }

    public fun getToken() : String{
        return token
    }

    public fun setToken(token : String){
        this.token = token
    }
}