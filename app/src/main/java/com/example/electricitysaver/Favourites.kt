package com.example.electricitysaver

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favourite_list.*

class Favourites : AppCompatActivity() {

    lateinit var adapter : RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favourite_list)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }


        layoutManager = LinearLayoutManager(this)
        pRecycler.layoutManager =layoutManager

        adapter = PaymentRecyclerAdapter()
        pRecycler.adapter = adapter


    }
}