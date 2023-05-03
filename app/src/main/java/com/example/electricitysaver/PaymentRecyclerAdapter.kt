package com.example.electricitysaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class PaymentRecyclerAdapter(private var paymentList: ArrayList<Payment>) :
    RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder>() {

//    val aname:Array<String> = arrayOf("Pamitha","Lokuge")
//    val anumber:Array<String> = arrayOf("12345678","98745612")



     class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nameTextView: EditText = itemView.findViewById(R.id.edtCardName)
        val accountTextView: EditText = itemView.findViewById(R.id.edtCardNo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fav_card_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment = paymentList[position]
        holder.nameTextView.setText(payment.name)
        holder.accountTextView.setText(payment.account)
    }

    override fun getItemCount() = paymentList.size

    fun setItems(items: ArrayList<Payment>) {
        this.paymentList = items
    }

}


