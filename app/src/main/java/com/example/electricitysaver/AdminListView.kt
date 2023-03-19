package com.example.electricitysaver

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminListView : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_list_bottomsheet)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val recyclerView : RecyclerView = findViewById(R.id.adminItemReview)
        adapter = AdminListAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<AdminListItem>()
        items.add(AdminListItem("Category 1",2.5))
        items.add(AdminListItem("Category 2", 5.2))
        items.add(AdminListItem("Category 3", 10.0))
        items.add(AdminListItem("Category 4", 9.4))
        items.add(AdminListItem("Category 5", 27.2))
        items.add(AdminListItem("Category 6", 30.0))
        items.add(AdminListItem("Category 7", 11.7))
        items.add(AdminListItem("Category 8", 18.9))

        adapter.setItems(items)
        adapter.notifyDataSetChanged()

    }
}