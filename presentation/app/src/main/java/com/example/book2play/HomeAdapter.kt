package com.example.book2play

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.book2play.CreateBooking.SearchDayScreen
import kotlinx.android.synthetic.main.home_row.view.*


class HomeAdapter(val arrayList:ArrayList<HomeAdvertiseModel>, val context: Context) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    var mContext = context


    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var myItemClickListener : MyItemClickListener?=null
        init {
            itemView.setOnClickListener(this)
        }
        fun bindItems(model: HomeAdvertiseModel) {
            itemView.name.text = model.name
            itemView.city.text = model.city
            itemView.location.text = model.location
            itemView.price.text = model.price
            itemView.sales.text = model.sales
            itemView.imageVi.setImageResource(model.image)
            itemView.rating_bar.setRating(model.rate.toFloat())
            itemView.oldprice.text = model.oldprice
            itemView.oldprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        // for Strategy pattern for 2 button
        fun setOnMyItemClickListener(ItemClickListener: MyItemClickListener){
            this.myItemClickListener = ItemClickListener
        }
        override fun onClick(v: View?) {
            this.myItemClickListener!!.onItemClickListener(v!!, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_row,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
//        val myItemClickListener : MyItemClickListener{
//
//        }

        // real implementation of each button
        holder.setOnMyItemClickListener(object :
            MyItemClickListener (mContext){
            override fun onItemClickListener(view: View, pos: Int)  {
                // open detail screen
                val bookingInfo = com.example.Type.MyBookingModel(player = "player1",
                                                    city = arrayList[pos].city.toString(),
                                                    center = arrayList[pos].name.toString()
                )
                Log.d("make booking", "Home adapter: "+ bookingInfo.toString())
                val intent = Intent(mContext, SearchDayScreen::class.java)
                val bundle =Bundle()
                bundle.putSerializable("BookingInfo", bookingInfo)
                bundle.putString("CENTERNAME", arrayList[pos].name.toString())
                intent.putExtras(bundle)
//                intent.putExtra("BookingInfo", bookingInfo)
//                intent.putExtra("CENTERNAME", arrayList[pos].name.toString())
                mContext.startActivity(intent)
            }
        })
    }
    abstract class MyItemClickListener(context: Context){
        abstract fun onItemClickListener(view: View, pos: Int)
    }
}