package com.example.electricitysaver

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class ListAdapter(private var items: ArrayList<ListItem>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View, private val items: ArrayList<ListItem>) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.icategory)
        val textView1: TextView = itemView.findViewById(R.id.uhours)
        val imageView1: ImageView = itemView.findViewById(R.id.editPayment)
        val imageView2: ImageView = itemView.findViewById(R.id.deletelist)
        init {
            imageView1.setOnClickListener {
                val id = items[adapterPosition].id
                val intent = Intent(itemView.context, UserUpdateEditItems::class.java).apply {
                    putExtra("ID", id.toString())
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_recycler_view_layout, parent, false)
        return ViewHolder(view, items)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.category
        holder.textView1.text = item.hours
        holder.imageView1.setImageResource(R.drawable.ic_baseline_edit_note_blue)
        holder.imageView2.setImageResource(R.drawable.ic_baseline_delete_outline_24)

        holder.imageView2.setOnClickListener {
            val id = item.id
            val helper = UserItemDbHelper(holder.itemView.context)
            val db = helper.writableDatabase
            val rowsAffected = db.delete("USER_ADD_ITEM", "_id = ?", arrayOf(id.toString()))
            if (rowsAffected > 0) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: ArrayList<ListItem>) {
        this.items = items
    }



}


