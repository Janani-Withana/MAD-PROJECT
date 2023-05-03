package com.example.electricitysaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class PaymentRecyclerAdapter :RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder>() {

    val aname:Array<String> = arrayOf("Pamitha","Lokuge")
    val anumber:Array<String> = arrayOf("12345678","98745612")



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val Aname : EditText
        val Anumber: EditText

        init {
            Aname =itemView.findViewById(R.id.edtCardName)
            Anumber = itemView.findViewById(R.id.edtCardNo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fav_card_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Aname.setText(aname[position])
        holder.Anumber.setText(anumber[position])
    }

    override fun getItemCount(): Int {
        return aname.size
    }

}

