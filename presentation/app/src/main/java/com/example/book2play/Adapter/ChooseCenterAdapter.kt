package com.example.book2play.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Type.MyBookingModel
import com.example.Type.MyCenterModel
import com.example.book2play.ScreenView.Activity.ChooseDayScreen
import com.example.book2play.R
import kotlinx.android.synthetic.main.rows_court_list.view.*

class ChooseCenterAdapter(val arrayList: ArrayList<MyCenterModel>, val context: Context, val bookingInfo: MyBookingModel?) :
    RecyclerView.Adapter<ChooseCenterAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var myItemClickListener : MyItemClickListener?=null

        init {
            itemView.setOnClickListener(this)
        }

        fun bindItem(model: MyCenterModel){
            itemView.image.setImageResource(model.avatar)
            itemView.textViewDescription.text = model.centerName
            itemView.textViewDetail.text = model.address
        }

        fun setOnMyItemClickListener(ItemClickListener: MyItemClickListener){
            this.myItemClickListener = ItemClickListener
        }
        // when item click
        override fun onClick(v: View?) {
            this.myItemClickListener!!.onItemClickListener(v!!, adapterPosition)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rows_court_list, parent, false)
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
                val intent = Intent(context, ChooseDayScreen::class.java)
                // update bookingInfo
                if (bookingInfo != null) {
                    bookingInfo.center = arrayList[position].centerName
                }
                else {
                    val bookingInfo =
                        MyBookingModel(center = arrayList[position].centerName)
                }

                intent.putExtra("BookingInfo", bookingInfo)
                intent.putExtra("CENTERNAME", arrayList[position].centerName)
                context.startActivity(intent)
            }
        })
    }

    interface MyItemClickListener{
        fun onItemClickListener(view: View, pos: Int)
    }
}