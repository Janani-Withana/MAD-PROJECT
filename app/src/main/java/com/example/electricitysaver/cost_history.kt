package com.example.electricitysaver

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.electricitysaver.databaseHelper.CostCalculationDbHelper

class cost_history : AppCompatActivity() {

    lateinit var db : SQLiteDatabase
    lateinit var rs : Cursor
    lateinit var adapter : SimpleCursorAdapter

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cost_history)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        var helper = CostCalculationDbHelper(applicationContext)
        db = helper.readableDatabase


        // Get the data from the database
        rs = db.rawQuery("SELECT * FROM COST_CALCULATION", null)

        //delete whole DB history
        val clearButton: Button = findViewById(R.id.btn_his_Clear)
        clearButton.setOnClickListener {
            // Show a confirmation dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Clear database")
            builder.setMessage("Are you sure you want to clear the History?")
            builder.setPositiveButton("Yes") { _, _ ->
                // Clear the whole database
                db.execSQL("DELETE FROM COST_CALCULATION")
                // Update the list view
                rs.requery()
                adapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("No") { _, _ ->
                // Do nothing
            }
            builder.show()
        }

        // Convert the data to a list of strings
        val dataList = mutableListOf<String>()
        while (rs.moveToNext()) {
            val date = rs.getString(rs.getColumnIndex("DATE"))
            val days = rs.getDouble(rs.getColumnIndex("DAYS"))
            val units = rs.getDouble(rs.getColumnIndex("UNITS"))
            val totalCost = rs.getDouble(rs.getColumnIndex("TOTAL_COST"))
            val fixedCharge = rs.getDouble(rs.getColumnIndex("FIXED_CHARGE"))
            val importCharge = rs.getDouble(rs.getColumnIndex("IMPORT_CHARGE"))
            // Use the values
            val dataString = "Date: $date \nDays: $days \t\t Units: $units \nTotal Cost: $totalCost \nFixed Charge: $fixedCharge \nImport Charge: $importCharge"
            dataList.add(dataString)
        }

        // Display the data in the ListView
        val listView: ListView = findViewById(R.id.hisListView)
        adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_1,
            rs,
            arrayOf("DATE"),
            intArrayOf(android.R.id.text1),
            0
        )
        listView.adapter = adapter

        // Register the ListView for context menu
        registerForContextMenu(listView)

        // Set the click listener for each item to show the full details
        listView.setOnItemClickListener { parent, view, position, id ->
            rs.moveToPosition(position)
            val date = rs.getString(rs.getColumnIndex("DATE"))
            val days = rs.getDouble(rs.getColumnIndex("DAYS"))
            val units = rs.getDouble(rs.getColumnIndex("UNITS"))
            val totalCost = rs.getDouble(rs.getColumnIndex("TOTAL_COST"))
            val fixedCharge = rs.getDouble(rs.getColumnIndex("FIXED_CHARGE"))
            val importCharge = rs.getDouble(rs.getColumnIndex("IMPORT_CHARGE"))
            // Use the values
            val dataString = "Date: $date \nDays: $days \t\t Units: $units \nTotal Cost: $totalCost \nFixed Charge: $fixedCharge \nImport Charge: $importCharge"
            AlertDialog.Builder(this)
                .setTitle("Details")
                .setMessage(dataString)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(101,11,1,"DELETE")
        menu?.setHeaderTitle("Removing Data")
    }

    @SuppressLint("Range")
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        rs.moveToPosition(position)

        if (item.itemId == 11) {
            db.delete("COST_CALCULATION", "_id = ?", arrayOf(rs.getString(rs.getColumnIndex("_id"))))
            rs = db.rawQuery("SELECT * FROM COST_CALCULATION", null)
            adapter.swapCursor(rs)
            return true
        }
        return super.onContextItemSelected(item)
    }
}
