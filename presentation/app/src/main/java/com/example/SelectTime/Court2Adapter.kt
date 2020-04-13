package com.example.SelectTime

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.book2play.R
import kotlinx.android.synthetic.main.court2_row.view.*


class Court2Adapter(val arrayList: ArrayList<Model>, val context: Context) :
    RecyclerView.Adapter<Court2Adapter.ViewHolder>() {
    var mContext = context


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var myItemClickListener : MyItemClickListener?=null
        init {
            itemView.setOnClickListener(this)

        }

        fun bindItems(model: Model){
            itemView.timeDisplay.text = model.time
            itemView.description.text = model.des

            if (model.slot == 1){

                itemView.description.text = "Click to choose slot"
                itemView.description.setBackgroundColor(Color.parseColor("#ffffff"))
            }


        }
        fun setOnMyItemClickListener(ItemClickListener: MyItemClickListener){
            this.myItemClickListener = ItemClickListener
        }

        override fun onClick(v: View?) {
            this.myItemClickListener?.onItemClickListener(v!!, adapterPosition)
        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from (parent.context).inflate(R.layout.court2_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.setOnMyItemClickListener(object  :
            MyItemClickListener {
            override fun onItemClickListener(view: View, position: Int) {
//                Toast.makeText(mContext, "Click on item with status: " + arrayList[pos].slot, Toast.LENGTH_SHORT).show()
                if (arrayList[position].slot == 0){
                    Toast.makeText(
                        mContext,
                        "This slot is not available now",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (arrayList[position].slot == 1){
                    Toast.makeText(context, "Chosen slot clicked", Toast.LENGTH_SHORT).show()
                    holder.itemView.description.setText("Slot is chosen")
                    holder.itemView.description.setBackgroundColor(Color.parseColor("#bffcc6"))
                    arrayList[position].slot = 2
                } else if (arrayList[position].slot == 2) {
                    Toast.makeText(context, "Chosen slot cancelled", Toast.LENGTH_SHORT).show()
                    holder.itemView.description.setText("Click to choose slot")
                    holder.itemView.description.setBackgroundColor(Color.parseColor("#ffffff"))
                    arrayList[position].slot = 1
                }
            }
        })

    }
    interface MyItemClickListener{
        fun onItemClickListener(view: View, pos: Int)
    }
}


