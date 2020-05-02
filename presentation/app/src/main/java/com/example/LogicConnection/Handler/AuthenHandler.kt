package com.example.LogicConnection.Handler

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.LogicConnection.AsyncClass.*
import com.example.book2play.MyApplication
import com.example.book2play.ScreenView.Activity.MainActivity
import java.net.HttpURLConnection

class AuthenHandler {
    companion object{
        fun loginWithFb(activity: Activity, token: String){
            (activity.application as MyApplication).setToken(token)
            (activity.application as MyApplication).setTokenType("FB")
            val status = CheckPlayerExistAsync(activity).execute().get()
            // player not exist
            if (status == HttpURLConnection.HTTP_UNAUTHORIZED.toString()){
                CreatePlayerAsync(activity).execute()
            }
        }

        fun signupPlayer(activity: Activity, playerId: String, password: String){
            SignupPlayerAsync(activity).execute(playerId, password)
        }

        fun loginPlayer(activity: Activity, playerId: String, password: String){
            val token = LoginPlayerAsync(activity).execute(playerId, password).get()
            if (token != null){
                (activity.application as MyApplication).setToken(token)
                (activity.application as MyApplication).setTokenType("DB")
            }
        }

        fun logoutPlayer(activity: Activity, token: String){
            val status = LogoutPlayerAsync(activity).execute().get()
            if (status == HttpURLConnection.HTTP_OK.toString()){
                (activity.application as MyApplication).setToken("")
                (activity.application as MyApplication).setTokenType("")
            }
        }
    }
}