package com.example.book2play

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val arrayList = ArrayList<CourtModel>()
        arrayList.add(CourtModel("BaoDoi Sporting Center", "145 Le Duan Street",  "Binh Duong city",5.0,R.drawable.sancaulong,"20% off for 1 month","40000 VND","50000 VND"))
        arrayList.add(CourtModel("HieuLon Sporting Center", "199 Phan Xich Long Street",  "Vung Tau city",4.5,R.drawable.sancaulong,"20% off for 1 month","40000 VND","50000 VND"))
        arrayList.add(CourtModel("PhatCho Sporting Center", "59 Dien Bien Phu Street",  "My Tho city",3.5,R.drawable.sancaulong,"20% off for 1 month","40000 VND","50000 VND"))

        val myAdapter = HomeAdapter(arrayList,this)
        recyclerView4.layoutManager= LinearLayoutManager(activity)
        recyclerView4.adapter = myAdapter



    }

}
