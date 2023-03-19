package com.example.electricitysaver

import android.graphics.Color
import android.os.Build
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

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val recyclerView : RecyclerView = findViewById(R.id.adminitemreview)
        adapter = adminListAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<adminList>()
        items.add(adminList("brand 1", 32.01))
        items.add(adminList("brand 2", 40.65))
        items.add(adminList("brand 3", 100.2))
        items.add(adminList("brand 4", 120.9))
        items.add(adminList("brand 5", 12.67))
        items.add(adminList("brand 6", 18.09))
        items.add(adminList("brand 7", 5.145))
        items.add(adminList("brand 8", 45.23))
        items.add(adminList("brand 9", 60.98))

        adapter.setItems(items)
        adapter.notifyDataSetChanged()
    }
}