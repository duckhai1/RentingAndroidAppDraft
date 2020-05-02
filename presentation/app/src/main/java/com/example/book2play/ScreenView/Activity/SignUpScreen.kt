package com.example.book2play.ScreenView.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.LogicConnection.Handler.AuthenHandler
import com.example.book2play.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.screen_sign_up.*
import java.io.FileNotFoundException
import java.io.InputStream

class SignUpScreen : AppCompatActivity() {


    var imageView: ImageView? = null
    var button: Button? = null
    private var inputUsername: EditText? = null
    private var inputFullName: EditText? = null
    private var inputEmail: EditText? = null
    private var inputPhone: EditText? = null
    private var inputPassword: EditText? = null
    private var inputLayoutUsername: TextInputLayout? = null
    private var inputLayoutFullName: TextInputLayout? = null
    private var inputLayoutEmail: TextInputLayout? = null
    private var inputLayoutPhone: TextInputLayout? = null
    private var inputLayoutPassword: TextInputLayout? = null
    private var btnSignup: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_sign_up)

        val toolbar = findViewById<View>(R.id.toolbarSU) as Toolbar
        toolbar.setTitle("Create account")
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        imageView = findViewById<View>(R.id.profilePicture) as ImageView
        button = findViewById<View>(R.id.addProPic) as Button

        inputLayoutUsername = findViewById<View>(R.id.input_layout_username) as TextInputLayout
        inputLayoutFullName = findViewById<View>(R.id.input_layout_fullname) as TextInputLayout
        inputLayoutEmail = findViewById<View>(R.id.input_layout_email) as TextInputLayout
        inputLayoutPhone = findViewById<View>(R.id.input_layout_phone) as TextInputLayout
        inputLayoutPassword = findViewById<View>(R.id.input_layout_password) as TextInputLayout

        inputUsername = findViewById<View>(R.id.username) as EditText
        inputFullName = findViewById<View>(R.id.fullname) as EditText
        inputEmail = findViewById<View>(R.id.email) as EditText
        inputPhone = findViewById<View>(R.id.phoneNumber) as EditText
        inputPassword = findViewById<View>(R.id.password) as EditText

        inputUsername!!.addTextChangedListener(MyTextWatcher(inputUsername))
        inputFullName!!.addTextChangedListener(MyTextWatcher(inputFullName))
        inputEmail!!.addTextChangedListener(MyTextWatcher(inputEmail))
        inputPhone!!.addTextChangedListener(MyTextWatcher(inputPhone))
        inputPassword!!.addTextChangedListener(MyTextWatcher(inputPassword))

        btnSignup = findViewById<View>(R.id.signUpBtn) as Button
        btnSignup!!.setOnClickListener {
            submitForm()
            val playerId = username.text.toString()
            val password = password.text.toString()
            Log.d("login", "signup screen: "+playerId +"/"+password)
            AuthenHandler.signupPlayer(this, playerId, password)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun submitForm() {
        if (!validateUsername()) {
            return
        }
        if (!validateFullName()) {
            return
        }
        if (!validateEmail()) {
            return
        }
        if (!validatePhone()) {
            return
        }
        if (!validatePassword()) {
            return
        }
    }

    //VALIDATE FOR EACH COMPONENTS IN REGISTRATION
    private fun validateUsername(): Boolean {
        val userName = inputUsername!!.text.toString().trim { it <= ' ' }
        if (userName.isEmpty()) {
            inputLayoutUsername!!.error = getString(R.string.err_msg_username)
            requestFocus(inputUsername)
            return false
        } else {
            inputLayoutUsername!!.isErrorEnabled = false
        }
        return true
    }

    private fun validateFullName(): Boolean {
        val fullName = inputFullName!!.text.toString().trim { it <= ' ' }
        if (fullName.isEmpty()) {
            inputLayoutFullName!!.error = getString(R.string.err_msg_name)
            requestFocus(inputFullName)
            return false
        } else {
            inputLayoutFullName!!.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val email = inputEmail!!.text.toString().trim { it <= ' ' }
        if (email.isEmpty() || !isValidEmail(
                email
            )
        ) {
            inputLayoutEmail!!.error = getString(R.string.err_msg_email)
            requestFocus(inputEmail)
            return false
        } else {
            inputLayoutEmail!!.isErrorEnabled = false
        }
        return true
    }

    private fun validatePhone(): Boolean {
        val phoneNumber = inputPhone!!.text.toString().trim { it <= ' ' }
        if (phoneNumber.isEmpty()) {
            inputLayoutPhone!!.error = getString(R.string.err_msg_phone)
            requestFocus(inputPhone)
            return false
        } else {
            inputLayoutPhone!!.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        val password = inputPassword!!.text.toString().trim { it <= ' ' }
        if (password.isEmpty()) {
            inputLayoutPassword!!.error = getString(R.string.err_msg_password)
            requestFocus(inputPassword)
            return false
        } else {
            inputLayoutPassword!!.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(v: View?) {
        if (v!!.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    private inner class MyTextWatcher(private val view: View?) :
        TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            when (view!!.id) {
                R.id.username -> validateUsername()
                R.id.fullname -> validateFullName()
                R.id.email -> validateEmail()
                R.id.phoneNumber -> validatePhone()
                R.id.password -> validatePassword()
            }
        }

    }

    //From down here they are just the functions for adding a profile pic
    //this method invokes when user click "+" button
    fun onImageGalleryClicked(v: View?) {
        //invoke image gallery with implicit intent
        val photoPickerIntent = Intent(Intent.ACTION_PICK)

        //place to find data
        val pictureDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val pictureDirectoryPath = pictureDirectory.path

        //get URI presentation
        val data = Uri.parse(pictureDirectoryPath)

        ///set data and type (get all image types)
        photoPickerIntent.setDataAndType(data, "image/*")
        startActivityForResult(
            photoPickerIntent,
            IMAGE_GALLERY_REQUEST
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                //process when get the data from image gallery
                val imageUri = data!!.data //address of the image on SD card

                //Declare a stream to read the image data
                val inputStream: InputStream?
                try {
                    inputStream = contentResolver.openInputStream(imageUri!!)

                    //get bitmap form stream
                    val image = BitmapFactory.decodeStream(inputStream)
                    imageView!!.setImageBitmap(image)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val IMAGE_GALLERY_REQUEST = 20
        private fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        }
    }
}