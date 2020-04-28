package com.example.book2play.ScreenView.Fragment


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.Type.MyBookingModel
import com.example.book2play.R
import com.example.LogicConnection.Handler.ApiHandler
import com.example.book2play.Adapter.CourtAdapter
import com.example.book2play.DisplayHelper.SelectActionModeInterface
import com.example.book2play.DisplayHelper.TimeModel
import kotlinx.android.synthetic.main.fragment_court_timeline.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class ChooseTimeCourtFragment : Fragment(),
    SelectActionModeInterface {
    var bookingInfo : MyBookingModel = MyBookingModel()

    lateinit var bookingCourtName :String

    var actionMode: ActionMode? = null
    var myAdapter: CourtAdapter? = null
    var mActivity : AppCompatActivity? = null

    override fun onAttach(context: Context) {
        mActivity = activity as AppCompatActivity
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_court_timeline, container, false)
        bookingInfo = this.arguments?.getSerializable("BookingInfo") as MyBookingModel
        bookingCourtName = this.arguments!!.getString("courtName").toString()
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // setup array of time
        val arrayList = setupTimeArray(15)

        recyclerView1.layoutManager = LinearLayoutManager(activity)
        myAdapter = context?.let {
            CourtAdapter(
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
                        bookingInfo.court = bookingCourtName
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
            mActivity?.supportActionBar?.hide()
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
            mActivity?.supportActionBar?.show()
            actionMode = null
            shouldResetRecyclerView = true
        }
    }
    override fun ActionModeInterface(size: Int) {
        if (actionMode == null) actionMode = activity?.startActionMode(ActionModeCallback())
        if (size > 0) actionMode?.setTitle("Number of slot choose: $size")
        else actionMode?.finish()
    }


    fun setupTimeArray(timeInterval: Int) : ArrayList<TimeModel>{
        // TODO get open, close from API
        // ApiHandler.getOpenTime(sportcenterId)
        // ApiHandler.getSlot(courtId)
        val open: String = "07:00:00"
        val close: String = "21:00:00"

        val arrayList = ArrayList<TimeModel>()

        val sdf1 = SimpleDateFormat("HH:mm:ss")
        val sdf2 = SimpleDateFormat("HH:mm")
        val openTime = sdf1.parse(open)
        val closeTime = sdf1.parse(close)
        val calendar = GregorianCalendar.getInstance()
        calendar.time = openTime
        while (calendar.time.before(closeTime) || calendar.time.equals(closeTime)){
            val curTime = sdf2.format(calendar.time)
            val timeModel = TimeModel(curTime, "Unavailable", 0)        // TODO change hardcode
            arrayList.add(timeModel)
            calendar.add(Calendar.MINUTE, timeInterval)
        }

        return arrayList
    }

}
