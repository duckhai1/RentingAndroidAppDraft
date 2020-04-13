package com.example.locationsscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.book2play.MainActivity
import com.example.book2play.R
import com.example.booking.SelectTimeScreen
import com.facebook.login.LoginManager
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.facebook_user_info.*

class LoginFacebookScreen : AppCompatActivity() {
    private var button: Button? = null
    private var circleImageView: CircleImageView? = null
    private var txtName: TextView? = null
    private var txtEmail: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.facebook_user_info)
        circleImageView = findViewById(R.id.profilePic)
        txtName = findViewById(R.id.profileName)
        txtEmail = findViewById(R.id.profileEmail)
        button = findViewById(R.id.signoutBtn)

        val toolbar = findViewById<View>(R.id.toolBarFB) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Welcome!"

        //Get data from intent
        val firstname = intent.extras!!.getString("firstname")
        val lastname = intent.extras!!.getString("lastname")
        val email = intent.extras!!.getString("email")
        val image = intent.extras!!.getString("Image")

        if (firstname != null && lastname != null && email != null && image != null) {
            txtName?.text = firstname + " " + lastname
            txtEmail?.text = email
            Glide.with(this@LoginFacebookScreen).load(image).into(circleImageView!!)
        } else {
            Toast.makeText(applicationContext, "Data Not Found", Toast.LENGTH_SHORT).show()
        }

        button?.setOnClickListener(View.OnClickListener {
            LoginManager.getInstance().logOut()
            val intent = Intent(this@LoginFacebookScreen, LoginScreen::class.java)
            startActivity(intent)
            finish()
        })

        val ctnBtn =
            findViewById<ImageView>(R.id.nxtActivity)
        nxtActivity.setOnClickListener {
            val intent = Intent(
                applicationContext,
                MainActivity::class.java)
            intent.putExtra("userFname", firstname)
            intent.putExtra("userLname", lastname)
            intent.putExtra("userEmail", email)
            intent.putExtra("userImage", image)
            startActivity(intent)
        }
    }
}