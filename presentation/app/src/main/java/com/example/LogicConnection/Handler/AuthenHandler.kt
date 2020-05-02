package com.example.LogicConnection.Handler

import android.app.Activity
import android.util.Log
import com.example.LogicConnection.AsyncClass.CreatePlayerAsync
import com.example.LogicConnection.AsyncClass.LoginPlayerAsync
import com.example.LogicConnection.AsyncClass.LogoutPlayerAsync
import com.example.LogicConnection.AsyncClass.SignupPlayerAsync
import com.example.book2play.MyApplication
import com.google.android.gms.common.util.Hex
import org.apache.commons.codec.binary.Hex.encodeHexString
import java.net.HttpURLConnection
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class AuthenHandler {
    companion object{
        fun loginWithFb(activity: Activity, token: String){
            (activity.application as MyApplication).setToken(token)
            (activity.application as MyApplication).setTokenType("FB")
            CreatePlayerAsync(activity).execute()
        }

        fun signupPlayer(activity: Activity, playerId: String, password: String){
            val hashedPass : String = hashPassword(playerId, password)
            Log.d("Hash", "Hashed password to singup: " + hashedPass)
            SignupPlayerAsync(activity).execute(playerId, hashedPass)
        }

        fun loginPlayer(activity: Activity, playerId: String, password: String){
            val hashedPass : String = hashPassword(playerId, password)
            Log.d("Hash", "Hashed password to login: " + hashedPass)
            val token = LoginPlayerAsync(activity).execute(playerId, hashedPass).get()
            if (token != null){
                (activity.application as MyApplication).setToken(token)
                (activity.application as MyApplication).setTokenType("DB")
            }
        }

        fun logoutPlayer(activity: Activity){
            val status = LogoutPlayerAsync(activity).execute().get()
            if (status == HttpURLConnection.HTTP_OK.toString()){
                (activity.application as MyApplication).setToken("")
                (activity.application as MyApplication).setTokenType("")
            }
        }

        private fun hashPassword(playerId: String, password: String) : String{
            val salt = playerId
            val iterations = 10000
            val keyLength = 512
            val passwordChars: CharArray = password.toCharArray()
            val saltBytes = salt.toByteArray()

            val hashedBytes: ByteArray? = hashPbkdf2(passwordChars, saltBytes, iterations, keyLength)
            return Hex.bytesToStringLowercase(hashedBytes)
        }

        private fun hashPbkdf2(
            password: CharArray?,
            salt: ByteArray?,
            iterations: Int,
            keyLength: Int
        ): ByteArray? {
            return try {
                val skf: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                val spec = PBEKeySpec(password, salt, iterations, keyLength)
                val key: SecretKey = skf.generateSecret(spec)
                key.getEncoded()
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            } catch (e: InvalidKeySpecException) {
                throw RuntimeException(e)
            }
        }
    }
}