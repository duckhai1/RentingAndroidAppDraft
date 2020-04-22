package com.example.book2play

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_row.view.*


class HomeAdapter(val arrayList:ArrayList<CourtModel>, val context: HomeFragment) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(model: CourtModel) {
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
    }
}