package com.example.electricitysaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adminListAdapter(private var items: ArrayList<adminList>):
    RecyclerView.Adapter<adminListAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.icategory)
        val textView1: TextView = itemView.findViewById(R.id.uhours)
        val imageView1: ImageView = itemView.findViewById(R.id.editlistwaren1)
        val imageView2: ImageView = itemView.findViewById(R.id.deletelist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_recycler_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.brand //data class variables
        holder.textView1.text = item.watt.toString() //data class variables
        holder.imageView1.setImageResource(R.drawable.ic_baseline_edit_note_blue)
        holder.imageView2.setImageResource(R.drawable.ic_baseline_delete_outline_24)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: ArrayList<adminList>) {
        this.items = items
    }
}