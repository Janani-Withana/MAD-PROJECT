package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class expectedUnitList : AppCompatActivity() {

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter: ExpectedListAdapter
    private lateinit var btnHome: Button
    private lateinit var btnclear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expected_unit_list)

        var btnHome = findViewById<Button>(R.id.btnHome)
        var btnclear = findViewById<Button>(R.id.btnclear)

        supportActionBar?.hide()
        var helper = UserItemDbHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM USER_EXPECTED_ITEM ", null)

        val btnHomePage = findViewById<ImageView>(R.id.HomeUser)
        btnHomePage.setOnClickListener {
            val mainIntent = Intent(this, HomePage::class.java)
            startActivity(mainIntent)
            //finish()
        }

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

        btnclear.setOnClickListener {
            var helper = UserItemDbHelper(applicationContext)
            var db = helper.writableDatabase

            val alertDialog = AlertDialog.Builder(this).apply {
                setTitle("Clear All Items")
                setMessage("Are you sure you want to clear all items?")
                setPositiveButton("OK") { _, _ ->
                    db.delete("USER_EXPECTED_ITEM", null, null)
                    db.close()
                    adapter.setItems(ArrayList())
                    adapter.notifyDataSetChanged()
                }
            }
            alertDialog.show()

        }

       //when i press the home button it will go to the home page
        btnHome.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

    }
}