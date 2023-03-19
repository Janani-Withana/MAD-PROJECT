package com.example.electricitysaver

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class expectedUnitList : AppCompatActivity() {

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter: ExpectedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expected_unit_list)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val recyclerView2 :RecyclerView = findViewById(R.id.review2)
        adapter = ExpectedListAdapter(ArrayList())
        recyclerView2.adapter = adapter
        recyclerView2.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<ExpectedListItem>()
        items.add(ExpectedListItem("Category 1", "10 hours" , "8 hours"))
        items.add(ExpectedListItem("Category 2", "5 hours" , "4 hours"))
        items.add(ExpectedListItem("Category 3", "6 hours" , "4 hours"))
        items.add(ExpectedListItem("Category 4", "8 hours" , "7 hours"))
        items.add(ExpectedListItem("Category 5", "3 hours" , "2 hours"))
        items.add(ExpectedListItem("Category 6", "8 hours" , "6 hours"))
        items.add(ExpectedListItem("Category 7", "4 hours" , "3 hours"))
        items.add(ExpectedListItem("Category 8", "6 hours" , "5 hours"))
        items.add(ExpectedListItem("Category 9", "5 hours" , "4 hours"))
        items.add(ExpectedListItem("Category 10", "4 hours" , "3 hours"))

        adapter.setItems(items)
        adapter.notifyDataSetChanged()

    }
}