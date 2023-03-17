package com.example.electricitysaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val itemList : ArrayList<Items>):RecyclerView.Adapter<ListAdapter.MyviewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_recycler_view_layout,parent,false)
        return MyviewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val currentItems = itemList[position]
        holder.itemcat.text = currentItems.category
        holder.hours.text = currentItems.nHours
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class MyviewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemcat : TextView = itemView.findViewById(R.id.icategory)
        val hours : TextView = itemView.findViewById(R.id.uhours)

    }
}