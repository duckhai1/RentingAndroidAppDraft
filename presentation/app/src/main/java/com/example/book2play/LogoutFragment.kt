package com.example.book2play

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.book2play.Login.LoginScreen

/**
 * A simple [Fragment] subclass.
 */
class LogoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_logout, container, false)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(activity, LoginScreen::class.java)
            startActivity(intent)
        }, 1000)

        return v
    }

}
