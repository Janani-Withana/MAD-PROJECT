package com.example.electricitysaver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminListView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: adminListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_view)

        val recyclerView : RecyclerView = findViewById(R.id.adminitemreview)
        adapter = adminListAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<adminList>()
        items.add(adminList("LG 32 Inch Tv", "32W"))
        items.add(adminList("Singer Tv", "40W"))
        items.add(adminList("Damro Fridge", "100W"))
        items.add(adminList("Lg Iron", "120W"))
        items.add(adminList("Bulb(12W)", "12W"))
        items.add(adminList("Bulb(18W)", "18W"))
        items.add(adminList("Bulb(5W)", "5W"))
        items.add(adminList("Laptop", "45W"))
        items.add(adminList("Rice Cooker", "60W"))

        adapter.setItems(items)
        adapter.notifyDataSetChanged()
    }
}