package com.example.electricitysaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpectedListAdapter(private var items: ArrayList<ExpectedListItem>) :
    RecyclerView.Adapter<ExpectedListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.ecategory)
        val textView1: TextView = itemView.findViewById(R.id.edhours)
        val textView2: TextView = itemView.findViewById(R.id.emhours)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expectedist_recyclerview_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.category
        holder.textView1.text = item.hours
        holder.textView2.text = item.mhours

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: ArrayList<ExpectedListItem>) {
        this.items = items
    }
}