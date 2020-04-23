package com.example.book2play.Login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.book2play.CreateBooking.ChooseLocationScreen
import com.example.book2play.R
import com.facebook.*
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL

class LoginScreen : AppCompatActivity() {
    private var loginButton: LoginButton? = null
    private var callbackManager: CallbackManager? = null
    var profile_pic: URL? = null
    var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        val appName = "BOOK2PLAY"
        val an = SpannableString(appName)
        an.setSpan(RelativeSizeSpan(2f), 4, 5, 0)
        val appNameTxtView = findViewById<View>(R.id.appName) as TextView
        appNameTxtView.text = an


        //there's no database of the user yet, so click on Sign in button go straight to location screen
        //but since Khai has turned ChooseLocationScreen into a fragment, press the button will fucking crash the app
        val bt =
            findViewById<View>(R.id.signinBtn) as Button
        bt.setOnClickListener {
            val intent = Intent(applicationContext, ChooseLocationScreen::class.java)
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
                Log.i("accessToken", accessToken)
                val request = GraphRequest.newMeRequest(
                    loginResult.accessToken
                ) { `object`, response ->
                    Log.i("LoginScreen", response.toString())
                    val bFacebookData = getFacebookData(`object`)
                    val firstname = bFacebookData.getString("first_name")
                    val lastname = bFacebookData.getString("last_name")
                    val email = bFacebookData.getString("email")
                    val profilepic = bFacebookData.getString("profile_pic")
                    val intent =
                        Intent(this@LoginScreen, LoginFacebookScreen::class.java)
                    intent.putExtra("firstname", firstname)
                    intent.putExtra("lastname", lastname)
                    intent.putExtra("email", email)
                    intent.putExtra("Image", profilepic)
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

    private fun getFacebookData(`object`: JSONObject): Bundle {
        val bundle = Bundle()
        try {
            id = `object`.getString("id")
            profile_pic = URL("https://graph.facebook.com/$id/picture?type=normal")
            Log.i("profile_pic", profile_pic.toString() + "")
            bundle.putString("profile_pic", profile_pic.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        bundle.putString("idFacebook", id)
        if (`object`.has("first_name")) {
            try {
                bundle.putString("first_name", `object`.getString("first_name"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        if (`object`.has("last_name")) {
            try {
                bundle.putString("last_name", `object`.getString("last_name"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        if (`object`.has("email")) {
            try {
                bundle.putString("email", `object`.getString("email"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return bundle
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }
}