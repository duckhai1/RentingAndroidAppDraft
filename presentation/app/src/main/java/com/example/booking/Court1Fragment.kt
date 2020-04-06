package com.example.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book2play.R
import kotlinx.android.synthetic.main.fragment_court1.*

/**
 * A simple [Fragment] subclass.
 */
class Court1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_court1, container, false)
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

        recyclerView1.layoutManager = LinearLayoutManager(activity)
        recyclerView1.adapter = context?.let { Court1Adapter(arrayList, it) }
    }

}
