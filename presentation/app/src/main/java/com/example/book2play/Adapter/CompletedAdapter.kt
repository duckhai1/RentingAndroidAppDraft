package com.example.book2play.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.Type.MyBookingModel
import com.example.book2play.R
import com.example.book2play.ScreenView.Activity.DetailScreen
import kotlinx.android.synthetic.main.row_completed.view.*

class CompletedAdapter(val arrayList: ArrayList<MyBookingModel>, val context : Context):
    RecyclerView.Adapter<CompletedAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener{
        var myItemClickListener : MyItemClickListener?=null

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        fun bindItem(model: MyBookingModel){
            itemView.record_date.text = model.date
            itemView.record_center.text = model.center
            itemView.record_player.text = model.player
            itemView.record_weekday.text = model.week
            itemView.record_time.text = model.time
            itemView.record_court.text = model.court
            if (model.isPaid == false){
                itemView.record_status.text = "Status: Unpaid"
                itemView.record_status.setTextColor(Color.parseColor("#fa7470"))
            } else {
                itemView.record_status.text = "Status: Paid"
                itemView.record_status.setTextColor(Color.parseColor("#81c784"))
            }
        }
        // for Strategy pattern for 2 button
        fun setOnMyItemClickListener(ItemClickListener: MyItemClickListener){
            this.myItemClickListener = ItemClickListener
        }
        // when item click
        override fun onClick(v: View?) {
            this.myItemClickListener!!.onItemClickListener(v!!, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            this.myItemClickListener!!.onItemLongClickListener(v!!, adapterPosition)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cancelled, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(arrayList[position])

        // real implementation of each button
        holder.setOnMyItemClickListener(object :
            MyItemClickListener {
            override fun onItemClickListener(view: View, pos: Int) {
                // open detail screen
                val intent = Intent(context, DetailScreen::class.java)
                intent.putExtra("BookingInfo", arrayList[pos])
                context.startActivity(intent)
            }

            override fun onItemLongClickListener(view: View, pos: Int) {
                Toast.makeText(context, "This feature still update", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // give an interface for each button of each booking have different function
    interface MyItemClickListener{
        fun onItemClickListener(view: View, pos: Int)
        fun onItemLongClickListener(view: View, pos: Int)
    }
}