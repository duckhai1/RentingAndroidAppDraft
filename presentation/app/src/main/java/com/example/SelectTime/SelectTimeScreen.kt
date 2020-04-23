package com.example.SelectTime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.LogicConnection.Type.MyBookingModel
import com.example.book2play.R
import kotlinx.android.synthetic.main.select_time_screen.*

class SelectTimeScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_time_screen)

        // get last intent information
        val bookingInfo = intent.getSerializableExtra("BookingInfo") as? MyBookingModel
        toolBar.setTitle("Booking")
        Log.d("make booking", "SelectTimeScreen: "+bookingInfo.toString())

        setSupportActionBar(toolBar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        // TODO change to more flexible to add court
        val fragmentAdapter =
            MyPagerAdapter(supportFragmentManager)
        val bundle = intent.extras

        setupFragment(fragmentAdapter,bundle, "Court 1")
        setupFragment(fragmentAdapter,bundle, "Court 2")
        setupFragment(fragmentAdapter,bundle, "Court 3")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)



    }


    fun setupFragment(fragmentAdapter: MyPagerAdapter, bundle: Bundle?, court_name : String){
        var court_frag = when (court_name){
            "Court 1" -> Court1Fragment()
            "Court 2" -> Court1Fragment()   // -> need to change
            "Court 3" -> Court1Fragment()   // -> need to change
            else -> Court3Fragment()
        }
        var cbundle = bundle?.clone() as Bundle
        if (cbundle != null) {
            cbundle.putString("courtName", court_name)
        }
        court_frag.arguments = cbundle
        fragmentAdapter.addFrag(court_frag, court_name)
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.action_mode_menu, menu)
//        return true
//    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId){
//            R.id.action_next ->{
//                val intent = Intent(this,BookSucessScrenn::class.java)
//                startActivity(intent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}

