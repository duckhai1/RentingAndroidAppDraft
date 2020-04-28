package com.example.book2play.ScreenView.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.Type.MyPlayerModel
import com.example.book2play.R
import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.screen_facebook_detail.*

class FacebookDetailScreen : AppCompatActivity() {
    var playerInfo :MyPlayerModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_facebook_detail)

        // get player information
        val playerInfo = intent.getSerializableExtra("PlayerInfo") as? MyPlayerModel
        Log.d("login", "playerInfo: "+ playerInfo.toString())

        val toolbar = findViewById<View>(R.id.toolBarFB) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Welcome!"

        var circleImageView : CircleImageView = findViewById(R.id.profilePic)
        if (playerInfo != null) {
            profileName.text = playerInfo.playerName
            profileEmail.text = playerInfo.email
            Glide.with(this@FacebookDetailScreen).load(playerInfo.image).into(circleImageView!!)
        } else {
            Toast.makeText(applicationContext, "Data Not Found", Toast.LENGTH_SHORT).show()
        }


        //LOGOUT BUTTON
        signoutBtn.setOnClickListener(View.OnClickListener {
            FacebookSdk.sdkInitialize(applicationContext)
            if (AccessToken.getCurrentAccessToken() == null) {
                return@OnClickListener  // user already logged out
            }
            GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                GraphRequest.Callback {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()
                    finish()
                }).executeAsync()
            val intent = Intent(this@FacebookDetailScreen, LoginScreen::class.java)
            startActivity(intent)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.next_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_next ->{
                val intent = Intent(applicationContext,MainActivity::class.java)
                intent.putExtra("PlayerInfo", playerInfo)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}