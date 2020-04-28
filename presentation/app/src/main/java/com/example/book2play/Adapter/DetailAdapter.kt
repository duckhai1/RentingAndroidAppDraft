package com.example.book2play.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.book2play.R
import kotlinx.android.synthetic.main.row_detail.view.*

class DetailAdapter(val detailList: ArrayList<String>, val descriptionList: ArrayList<String>, val context: Context ):
    RecyclerView.Adapter<DetailAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(detail: String, description: String){
            itemView.textViewDescription.text = description
            itemView.textViewDetail.text = detail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_detail, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return detailList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(detailList[position], descriptionList[position])
    }
}