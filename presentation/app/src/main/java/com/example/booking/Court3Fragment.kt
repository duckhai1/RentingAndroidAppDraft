package com.example.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_court3.*

/**
 * A simple [Fragment] subclass.
 */
class Court3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_court3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arrayList = ArrayList<Model>()
        var count = 0
        val time = arrayOfNulls<String>(56)
        for (i in 7..9) {
            time[count] = "0$i:00"
            for (y in 15..45 step 15) {
                count += 1
                time[count] = "0$i:$y"
            }
            count += 1
        }
        for (i in 10..20) {
            time[count] = "$i:00"
            for (y in 15..45 step 15) {
                count += 1
                time[count] = "$i:$y"
            }
            count += 1
        }
        var slot = arrayOfNulls<Int>(56)
        for (i in 0..55) {
            slot[i] = 0
        }
        for (i in 23..26) {
            slot[i] = 1
        }
        for (i in 0..55) {
            arrayList.add(Model(time[i], "Unavailable", slot[i]))
        }

        recyclerView3.layoutManager = LinearLayoutManager(activity)
        recyclerView3.adapter = context?.let { Court3Adapter(arrayList, it) }
    }


}

