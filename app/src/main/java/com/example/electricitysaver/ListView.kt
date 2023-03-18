package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val recyclerView :RecyclerView = findViewById(R.id.itemreview)
        adapter = ListAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<ListItem>()
        items.add(ListItem("Category 1", "10 hours"))
        items.add(ListItem("Category 2", "5 hours"))
        items.add(ListItem("Category 3", "6 hours"))
        items.add(ListItem("Category 4", "8 hours"))
        items.add(ListItem("Category 5", "3 hours"))
        items.add(ListItem("Category 6", "8 hours"))
        items.add(ListItem("Category 7", "4 hours"))
        items.add(ListItem("Category 8", "6 hours"))

        adapter.setItems(items)
        adapter.notifyDataSetChanged()

        val btnListView = findViewById<Button>(R.id.submit_btn)
        btnListView.setOnClickListener {
            val mainIntent = Intent(this, expectedUnitList::class.java)
            startActivity(mainIntent)
            //finish()
        }
    }

}