package com.example.booking
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.book2play.R
import kotlinx.android.synthetic.main.select_time_screen.*

class SelectTimeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_time_screen)

        val date_choosen = intent.getSerializableExtra("THEDATE")
        Toast.makeText(applicationContext, "Select time in date " +date_choosen, Toast.LENGTH_SHORT).show()

        nextActivity.setOnClickListener{
            val intent = Intent(this,BookSucessScrenn::class.java)
            startActivity(intent)
        }
        toolBar.setTitle("Booking")
        setSupportActionBar(toolBar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

