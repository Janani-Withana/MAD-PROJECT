package com.example.electricitysaver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListView : AppCompatActivity() {

    private lateinit var newRecylerview : RecyclerView
    private lateinit var newArrayList: ArrayList<Items>
    lateinit var itemcategory : Array<String>
    lateinit var ushours : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        itemcategory = arrayOf(
            "Tv",
            "Fridge",
            "Fan",
            "Rice Cooker",
            "Ac",
            "Bulb"
        )

        ushours = arrayOf(
            "5",
            "6",
            "7",
            "2",
            "8",
            "9"
        )

        newRecylerview = findViewById(R.id.itemreview)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)

        newArrayList = arrayListOf<Items>()
        getUserdata()



    }

    private fun getUserdata(){
        
    }
}