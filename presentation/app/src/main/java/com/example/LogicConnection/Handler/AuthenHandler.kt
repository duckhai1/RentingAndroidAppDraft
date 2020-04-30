package com.example.LogicConnection.Handler

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.AsyncClass.CheckPlayerExistAsync
import com.example.LogicConnection.AsyncClass.CreatePlayerAsync
import com.example.book2play.MyApplication
import java.net.HttpURLConnection

class AuthenHandler {
    companion object{
        fun loginWithFb(activity: Activity, token: String){
            (activity.application as MyApplication).setToken(token)

            val status = CheckPlayerExistAsync(activity).execute().get()
            // player not exist
            if (status == HttpURLConnection.HTTP_UNAUTHORIZED.toString()){
                CreatePlayerAsync(activity).execute()
            }
        }
    }
}