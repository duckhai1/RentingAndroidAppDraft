package com.example.book2play

import android.app.Application

class MyApplication : Application() {
    private var Token : String = ""

    public fun getToken() : String{
        return Token
    }

    public fun setToken(token : String){
        this.Token = token
    }
}