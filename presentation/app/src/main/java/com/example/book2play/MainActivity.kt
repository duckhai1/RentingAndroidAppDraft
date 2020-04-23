package com.example.book2play

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.book2play.BookingList.BookingHistoryScreen
import com.example.book2play.CreateBooking.ChooseCitycreen
import com.example.book2play.Login.LogoutScreen

import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // fragment for each menu item
    lateinit var homeFragment: HomeScreen
    lateinit var bookingHistoryFragment: BookingHistoryScreen
    lateinit var createBookingFragment : ChooseCitycreen
    lateinit var settingFragment: HomeScreen
    lateinit var logoutFragment: LogoutScreen
    lateinit var fragment1: HomeScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        actionBar?.title = "Navigation Drawer"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle (
            this, drawerLayout, toolBar, (R.string.open), (R.string.close)
        ) {

        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // default fragment
        homeFragment = HomeScreen()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home_menu -> {
                homeFragment = HomeScreen()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, homeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.create_booking_menu -> {
                createBookingFragment =
                    ChooseCitycreen()  // --> change this one
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, createBookingFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.booking_list_menu -> {
                bookingHistoryFragment =
                    BookingHistoryScreen()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, bookingHistoryFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.setting_menu -> {
                fragment1 =
                    HomeScreen()  // --> change this one
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment1)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.logout_menu -> {
                LoginManager.getInstance().logOut()
                logoutFragment =
                    LogoutScreen()  // --> change this one
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, logoutFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }


}
