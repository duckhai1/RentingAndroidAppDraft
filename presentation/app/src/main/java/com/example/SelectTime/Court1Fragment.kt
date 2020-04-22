package com.example.SelectTime


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.LogicConnection.Type.MyBookingModel
import com.example.book2play.R
import com.example.CreateBooking.BookSucessScrenn
import com.example.LogicConnection.Handler.ApiHandler
import com.example.book2play.MainActivity
import kotlinx.android.synthetic.main.fragment_court1.*
import java.lang.Exception


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
        for (i in 20..29) {
            slot[i] = 1
        }
        for (i in 0..55) {
            arrayList.add(
                Model(
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

                    Toast.makeText(context, timeArray?.get(0) +" "+timeArray?.get(1), Toast.LENGTH_LONG).show()

//                    // move to next screen
//                    val intent =
//                        Intent(activity, BookSucessScrenn::class.java)




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
                    activity?.let { ApiHandler.createBooking(bookingInfo, it) }  //ApiHandler.createBooking(bookingInfo, activity)
                    Log.d("server_connect", "Finish booking")
//                    intent.putExtra("BookingInfo", bookingInfo)
//                    try {
//                        activity?.let { ApiHandler.createBooking(bookingInfo, it) }  //ApiHandler.createBooking(bookingInfo, activity)
//                        Log.d("server_connect", "Finish booking")
//                        startActivity(intent)
//                    } catch (e: Exception){
//                        Log.d("server_connect", "catch fail to connect to server exception")
//                        val builder = AlertDialog.Builder(activity)
//
//                        builder.setTitle("Connection error")
//                        builder.setMessage("Can not connect to the server. Please check you internet connection")
//                        builder.setNeutralButton("OK"){dialog, which ->
//                            val back_intent = Intent(activity, MainActivity::class.java)
//                            startActivity(back_intent)
//                        }
//                        val dialog: AlertDialog = builder.create()
//                        dialog.show()
//                    }

                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.action_mode_menu, menu)
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
