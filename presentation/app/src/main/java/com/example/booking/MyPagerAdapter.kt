package com.example.booking
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> { Court1Fragment()
            }
            1 -> { Court2Fragment()
            }
            else -> {
            return Court3Fragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Court 1"
            1 -> "Court 2"
            else -> {
                return " Court 3"
            }
        }
    }
}