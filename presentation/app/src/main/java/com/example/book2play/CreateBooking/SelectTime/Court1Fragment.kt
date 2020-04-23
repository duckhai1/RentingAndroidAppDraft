package com.example.book2play.CreateBooking.SelectTime


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.Type.MyBookingModel
import com.example.book2play.R
import com.example.LogicConnection.Handler.ApiHandler
import kotlinx.android.synthetic.main.fragment_court1.*


/**
 * A simple [Fragment] subclass.
 */
class Court1Fragment : Fragment(), MainInterface {



    var bookingInfo : MyBookingModel =
        MyBookingModel()

    lateinit var bookingCourtName :String

    var actionMode: ActionMode? = null
    var myAdapter: Court1Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_court1, container, false)
        bookingInfo = this.arguments?.getSerializable("BookingInfo") as MyBookingModel
        bookingCourtName = this.arguments!!.getString("courtName").toString()
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        val arrayList = ArrayList<TimeModel>()
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
        for (i in 20..29) {
            slot[i] = 1
        }
        for (i in 0..55) {
            arrayList.add(
                TimeModel(
                    time[i],
                    "Unavailable",
                    slot[i]
                )
            )
        }

        recyclerView1.layoutManager = LinearLayoutManager(activity)
        myAdapter = context?.let {
            Court1Adapter(
                arrayList,
                it,
                this
            )
        }
        recyclerView1.adapter = myAdapter
    }

    inner class ActionModeCallback : ActionMode.Callback {
        var shouldResetRecyclerView = true
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.getItemId()) {
                // clear choose slot
                R.id.action_delete -> {
                    shouldResetRecyclerView = false
                    myAdapter?.clearSelectedIds()
                    actionMode?.setTitle("") //remove item count from action mode.
                    actionMode?.finish()
                    return true
                }
                // select choose slot
                R.id.action_next -> {
                    val timeArray = myAdapter?.chooseSelectedIds()
                    if (timeArray == null){
                        myAdapter?.clearSelectedIds()

                        return false
                    }
//                    Toast.makeText(context, timeArray?.get(0) +" "+timeArray?.get(1), Toast.LENGTH_LONG).show()
                    // update bookingInfo
                    if (bookingInfo != null) {
                        bookingInfo.start = timeArray?.get(0)
                        bookingInfo.end = timeArray?.get(1)
//                        bookingInfo.court = bookingCourtName
                        bookingInfo.court = "court1"   // <--- change this
                    }
                    else {
                        bookingInfo = MyBookingModel(
                            start = timeArray?.get(0),
                            end = timeArray?.get(1),
                            court = bookingCourtName
                        )
                    }

                    activity?.let { ApiHandler.createBooking(it,bookingInfo) }  //ApiHandler.createBooking(activity, bookingInfo)
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.choose_time_action_mode_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (shouldResetRecyclerView) {
                myAdapter?.selectedIds?.clear()
                myAdapter?.notifyDataSetChanged()
            }
            actionMode = null
            shouldResetRecyclerView = true
        }
    }
    override fun mainInterface(size: Int) {
        if (actionMode == null) actionMode = activity?.startActionMode(ActionModeCallback())
        if (size > 0) actionMode?.setTitle("Number of slot choose: $size")
        else actionMode?.finish()
    }

}
