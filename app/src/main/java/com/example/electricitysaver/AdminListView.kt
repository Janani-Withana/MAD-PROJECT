package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SimpleCursorAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.AdminItemDbHelper

class AdminListView : AppCompatActivity(),adminListAdapter.EditListener {

    private lateinit var recyclerView: RecyclerView


    private lateinit var helper: AdminItemDbHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var rs: Cursor
    private lateinit var adapter: adminListAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_view)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        helper = AdminItemDbHelper(applicationContext)
        db = helper.readableDatabase
        rs = db.rawQuery("SELECT * FROM Admin_Add_Item", null)


        val recyclerView : RecyclerView = findViewById(R.id.adminitemreview)
        adapter = adminListAdapter(ArrayList(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<adminList>()
        while (rs.moveToNext()) {
            var itemId = rs.getLong(0)
            var category = rs.getString(1)
            var brand = rs.getString(2)
            var watts = rs.getString(3).toDouble()

            //Add Item to the list
            items.add(adminList(itemId,category,brand,watts))

        }

        adapter.setItems(items)
        adapter.notifyDataSetChanged()
    }

    override fun onEditClicked(item: adminList) {
        val intent = Intent(this, UpdateAdminItemList::class.java).apply {
            putExtra("CATEGORY", item.category)
            putExtra("BRAND", item.brand)
            putExtra("WATTS", item.watt)
            putExtra("ID", item.itemId)
        }
        startActivity(intent)
    }

}