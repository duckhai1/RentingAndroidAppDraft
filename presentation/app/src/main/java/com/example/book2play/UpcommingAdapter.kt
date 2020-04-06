package com.example.book2play

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.upcomming_row.view.*

class UpcommingAdapter(val arrayList: ArrayList<MyBookingModel>, val context : Context):
    RecyclerView.Adapter<UpcommingAdapter.ViewHolder>() {
    var mContext = context

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView), View.OnClickListener{
        protected lateinit var detailButton: Button
        protected lateinit var cancelButton: Button
        var myButtonClickListener : MyButtonClickListener?=null

        init {
            detailButton = itemView.findViewById(R.id.detail_button) as Button
            cancelButton = itemView.findViewById(R.id.cancel_button) as Button

            detailButton.setOnClickListener(this)
            cancelButton.setOnClickListener(this)
        }

        // assign value
        fun bindItem(model: MyBookingModel){
            itemView.record_date.text = model.date
            itemView.record_title.text = model.title
            itemView.record_info.text = model.info
            itemView.record_weekday.text = model.week
        }

        // for Strategy pattern for 2 button
        fun setOnMyButtonClickListener(ButtonClickListener:MyButtonClickListener){
            this.myButtonClickListener = ButtonClickListener
        }

        // when button click
        override fun onClick(v: View?) {
            if (v != null) {
                if (v.id == detailButton.id){
                    this.myButtonClickListener!!.onDetailButtonClickListener(v!!, adapterPosition)
                } else if (v.id == cancelButton.id){
                    this.myButtonClickListener!!.onCancelButtonClickListener(v!!, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.upcomming_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(arrayList[position])

        // real implementation of each button
        holder.setOnMyButtonClickListener(object : MyButtonClickListener{
            override fun onDetailButtonClickListener(view: View, pos: Int) {
                // open detail screen
                val intent = Intent(mContext, BookingDetailDemo::class.java)
                intent.putExtra("BookingInfo", arrayList[pos])
                mContext.startActivity(intent)
            }

            override fun onCancelButtonClickListener(view: View, pos: Int) {
                // show confirm alert
                val builder = AlertDialog.Builder(mContext)

                builder.setTitle("Confirm")
                builder.setMessage("Are you sure you want to cancel this booking.\nThis step can not be undo")
                builder.setPositiveButton("YES"){dialog, which ->
                    Toast.makeText(mContext, "You have cancel the booking "+pos , Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("NO"){dialog, which ->
                    // do nothing
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        })
    }

    // give an interface for each button of each booking have different function
    interface MyButtonClickListener {
        fun onDetailButtonClickListener(view: View, pos:Int)
        fun onCancelButtonClickListener(view: View, pos:Int)
    }
}