package com.example.book2play.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.book2play.ScreenView.Fragment.CancelledFragment
import com.example.book2play.ScreenView.Fragment.CompletedFragment
import com.example.book2play.ScreenView.Fragment.UpcommingFragment

class BookingHistoryPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0->{
                UpcommingFragment()
            }
            1->{
                CancelledFragment()
            }
            else ->{
                return CompletedFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0-> "Upcomming"
            1-> "Cancelled"
            else ->{
                return "Completed"
            }
        }
    }
}