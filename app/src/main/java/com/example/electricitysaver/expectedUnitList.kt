package com.example.electricitysaver

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class expectedUnitList : AppCompatActivity() {

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter: ExpectedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expected_unit_list)

        supportActionBar?.hide()
        var helper = UserItemDbHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM USER_EXPECTED_ITEM ", null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val recyclerView2 :RecyclerView = findViewById(R.id.review2)
        adapter = ExpectedListAdapter(ArrayList())
        recyclerView2.adapter = adapter
        recyclerView2.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<ExpectedListItem>()
        while (rs.moveToNext()) {
            var category = rs.getString(1)
            var Dduration = rs.getString(2)
            var Mduration = rs.getString(3)

            items.add(ExpectedListItem(category, Dduration , Mduration))

        }


        adapter.setItems(items)
        adapter.notifyDataSetChanged()

    }
}