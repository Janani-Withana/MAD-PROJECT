package com.example.electricitysaver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminListAdapter (private var items: ArrayList<AdminListItem>) :
    RecyclerView.Adapter<AdminListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.category)
        val textView1: TextView = itemView.findViewById(R.id.watts)
        val imageView1: ImageView = itemView.findViewById(R.id.editItem)
        val imageView2: ImageView = itemView.findViewById(R.id.deleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_list_recycler_view, parent, false)
        return AdminListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.category
        holder.textView1.text = item.watts.toString()
        holder.imageView1.setImageResource(R.drawable.ic_baseline_edit_note_24)
        holder.imageView2.setImageResource(R.drawable.ic_baseline_delete_outline_24)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: ArrayList<AdminListItem>) {
        this.items = items
    }




}