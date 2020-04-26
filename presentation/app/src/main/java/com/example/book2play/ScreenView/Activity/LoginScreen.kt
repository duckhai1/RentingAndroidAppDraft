package com.example.book2play.ScreenView.Activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.Type.MyPlayerModel
import com.example.book2play.MyApplication
import com.example.book2play.ScreenView.Fragment.ChooseCityScreen
import com.example.book2play.R
import com.facebook.*
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.screen_login.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

class LoginScreen : AppCompatActivity() {
    private var loginButton: LoginButton? = null
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_login)
        val appName = "BOOK2PLAY"
        val an = SpannableString(appName)
        an.setSpan(RelativeSizeSpan(2f), 4, 5, 0)
        val appNameTxtView = findViewById<View>(R.id.appName) as TextView
        appNameTxtView.text = an


        // TODO check login authorization
        signinBtn.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        signupBtn.setOnClickListener {
            val intent = Intent(applicationContext, SignUpScreen::class.java)
            startActivity(intent)
        }

        //FACEBOOK PART
        FacebookSdk.sdkInitialize(applicationContext)
        loginButton = findViewById<View>(R.id.FBSigninBtn) as LoginButton
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY)
        callbackManager = CallbackManager.Factory.create()
        loginButton!!.setReadPermissions("email")
        LoginManager.getInstance().registerCallback(callbackManager, object :
//        loginButton!!.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken.token
                (this@LoginScreen.application as MyApplication).setToken(accessToken)
                Log.d("Token", accessToken)

                val request = GraphRequest.newMeRequest(loginResult.accessToken) { jsonObject, response ->
                    Log.i("LoginScreen", response.toString())
                    val playerInfo = getPlayer(jsonObject)
                    val intent = Intent(this@LoginScreen, FacebookDetailScreen::class.java)
                    intent.putExtra("PlayerInfo", playerInfo)
                    startActivity(intent)
                    finish()
                }

                val parameters = Bundle()
                parameters.putString("fields", "id, first_name, last_name, email")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {}
            override fun onError(error: FacebookException) {}
        })
    }

    private fun getPlayer(jsonObject: JSONObject) : MyPlayerModel? {
        var playerInfo : MyPlayerModel? =null
        try {
            val id = jsonObject.getString("id")
            val profile_pic = URL("https://graph.facebook.com/$id/picture?type=normal")
            val name = jsonObject.getString("first_name") + " "+  jsonObject.getString("last_name")
            val email = jsonObject.getString("email")
            playerInfo = MyPlayerModel(
                playerId = id,
                password = "pass1",      // TODO change here
                playerName = name,
                email = email,
                image = profile_pic.toString()
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return playerInfo
    }
    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }
}