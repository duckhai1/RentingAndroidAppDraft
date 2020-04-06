package com.example.book2play

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cancelled_row.view.*

class CancelledAdapter(val arrayList: ArrayList<MyBookingModel>, val context : Context):
    RecyclerView.Adapter<CancelledAdapter.ViewHolder>() {
    var mContext = context


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var myItemClickListener : MyItemClickListener?=null

        init {
            itemView.setOnClickListener(this)

        }
        fun bindItem(model: MyBookingModel){
            itemView.record_date.text = model.date
            itemView.record_title.text = model.title
            itemView.record_info.text = model.info
            itemView.record_weekday.text = model.week
        }
        // for Strategy pattern for 2 button
        fun setOnMyItemClickListener(ItemClickListener:MyItemClickListener){
            this.myItemClickListener = ItemClickListener
        }
        // when item click
        override fun onClick(v: View?) {
            this.myItemClickListener!!.onItemClickListener(v!!, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cancelled_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(arrayList[position])

        // real implementation of each button
        holder.setOnMyItemClickListener(object : MyItemClickListener{
            override fun onItemClickListener(view: View, pos: Int) {
                // open detail screen
                val intent = Intent(mContext, BookingDetailDemo::class.java)
                intent.putExtra("BookingInfo", arrayList[pos])
                mContext.startActivity(intent)
            }
        })
    }

    // give an interface for each button of each booking have different function
    interface MyItemClickListener{
        fun onItemClickListener(view: View, pos: Int)
    }
}