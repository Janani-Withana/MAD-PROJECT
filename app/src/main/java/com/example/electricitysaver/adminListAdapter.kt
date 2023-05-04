package com.example.electricitysaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adminListAdapter(private var items: ArrayList<adminList>,private val editListener: EditListener,
                       private val deleteListener: DeleteListener):
    RecyclerView.Adapter<adminListAdapter.ViewHolder>(){

    interface EditListener {
        fun onEditClicked(item: adminList)
    }
    interface DeleteListener {
        fun onDeleteClicked(item: adminList)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.category)
        val textView1: TextView = itemView.findViewById(R.id.brand)
        val textView2: TextView = itemView.findViewById(R.id.watts)
        val imageView1: ImageView = itemView.findViewById(R.id.editItem)
        val imageView2: ImageView = itemView.findViewById(R.id.deleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_list_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.category //data class variables
        holder.textView1.text = item.brand //data class variables
        holder.textView2.text = item.watt.toString() //data class variables
        holder.imageView1.setImageResource(R.drawable.ic_baseline_edit_note_blue)
        holder.imageView1.setOnClickListener {
            editListener.onEditClicked(item)
        }
        holder.imageView2.setImageResource(R.drawable.ic_baseline_delete_outline_24)
        holder.imageView2.setOnClickListener {
            deleteListener.onDeleteClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: ArrayList<adminList>) {
        this.items = items
    }
}