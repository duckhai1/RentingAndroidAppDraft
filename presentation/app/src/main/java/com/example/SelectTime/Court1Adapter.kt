package com.example.SelectTime

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.book2play.R
import kotlinx.android.synthetic.main.court1_row.view.*


class Court1Adapter(val arrayList: ArrayList<Model>, val context: Context, val mainInterface: MainInterface) :
    RecyclerView.Adapter<Court1Adapter.ViewHolder>() {
    var mContext = context
    val selectedIds: MutableList<String> = ArrayList<String>()

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
        val v = LayoutInflater.from (parent.context).inflate(R.layout.court1_row, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        val id = arrayList[position].time


        if (arrayList[position].slot == 0) {
            holder.itemView.description.setText("Unavailable")
            holder.itemView.description.setBackgroundColor(Color.parseColor("#fa7470"))
        } else {

            if (selectedIds.contains(id)) {
                holder.itemView.description.setText("Slot is chosen")
                holder.itemView.description.setBackgroundColor(Color.parseColor("#bffcc6"))
            } else {
                holder.itemView.description.setText("Click to choose slot")
                holder.itemView.description.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }

        holder.setOnMyItemClickListener(object  :
            MyItemClickListener {
            override fun onItemClickListener(view: View, position: Int) {
//                Toast.makeText(mContext, "Click on item with status: " + arrayList[pos].slot, Toast.LENGTH_SHORT).show()
                if (arrayList[position].slot == 0){
                    Toast.makeText(mContext,"This slot is not available now",Toast.LENGTH_LONG).show()
                } else if (arrayList[position].slot == 1){
//                    Toast.makeText(context, "Chosen slot clicked", Toast.LENGTH_SHORT).show()
                    arrayList[position].slot = 2
                    addIDIntoSelectedIds(position)
                } else if (arrayList[position].slot == 2) {
//                    Toast.makeText(context, "Chosen slot cancelled", Toast.LENGTH_SHORT).show()
                    arrayList[position].slot = 1
                    addIDIntoSelectedIds(position)
                }
            }
        })

    }

    fun addIDIntoSelectedIds(index: Int){
        val id = arrayList[index].time
        if (selectedIds.contains(id))
            selectedIds.remove(id)
        else
            id?.let { selectedIds.add(it) }


        notifyItemChanged(index)
        mainInterface.mainInterface(selectedIds.size)
    }

    fun clearSelectedIds() {
        if (selectedIds.size < 1) return
        selectedIds.clear()
        notifyDataSetChanged()
    }

    fun chooseSelectedIds(): MutableList<String>? {
        if (selectedIds.size < 4 || selectedIds.size > 6) {
            Toast.makeText(context, "Ammount of booking must between 45m and 1h30m", Toast.LENGTH_SHORT).show()
            return null
        }
        // TODO Check the condition pick time must be consecutive


        return selectedIds
    }

    interface MyItemClickListener{
        fun onItemClickListener(view: View, pos: Int)
    }
}


