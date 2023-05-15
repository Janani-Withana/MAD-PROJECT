package com.example.electricitysaver

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.CostCalculationDbHelper


class cost_history : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var db: SQLiteDatabase
    lateinit var rs: Cursor
    lateinit var adapter: DetailAdapter
    var selectedItemPosition = -1

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cost_history)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C")
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Query the database to get the payment calculation history
        val dbHelper = CostCalculationDbHelper(this)
        db = dbHelper.readableDatabase
        rs = db.rawQuery("SELECT * FROM COST_CALCULATION", null)

        // Create a list of details from the query results
        val details = mutableListOf<Detail>()
        while (rs.moveToNext()) {
            val date = rs.getString(rs.getColumnIndex("DATE"))
            val days = rs.getFloat(rs.getColumnIndex("DAYS"))
            val units = rs.getFloat(rs.getColumnIndex("UNITS"))
            val totalCost = rs.getFloat(rs.getColumnIndex("TOTAL_COST"))
            val fixedCharge = rs.getFloat(rs.getColumnIndex("FIXED_CHARGE"))
            val importCharge = rs.getFloat(rs.getColumnIndex("IMPORT_CHARGE"))

            val detail = Detail(
                "Date:\n$date",
                "Days: $days\nUnits: $units\nTotal Cost: $totalCost\nFixed Charge: $fixedCharge\nImport Charge: $importCharge"
            )
            details.add(detail)
        }

        //delete whole DB history
        val clearButton: Button = findViewById(R.id.btn_his_Clear)
        clearButton.setOnClickListener {
            // Show confirmation dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Clear database")
            builder.setMessage("Are you sure you want to clear the History?")
            builder.setPositiveButton("Yes") { _, _ ->
                try {
                    // Clear the whole database
                    db.execSQL("DELETE FROM COST_CALCULATION")
                    // Re-query the database
                    rs = db.rawQuery("SELECT * FROM COST_CALCULATION", null)
                    // Create a new list of details from the query results
                    val details = mutableListOf<Detail>()
                    while (rs.moveToNext()) {
                        val date = rs.getString(rs.getColumnIndex("DATE"))
                        val days = rs.getFloat(rs.getColumnIndex("DAYS"))
                        val units = rs.getFloat(rs.getColumnIndex("UNITS"))
                        val totalCost = rs.getFloat(rs.getColumnIndex("TOTAL_COST"))
                        val fixedCharge = rs.getFloat(rs.getColumnIndex("FIXED_CHARGE"))
                        val importCharge = rs.getFloat(rs.getColumnIndex("IMPORT_CHARGE"))

                        val detail = Detail(
                            "Date:\n$date",
                            "Days: $days\nUnits: $units\nTotal Cost: $totalCost\nFixed Charge: $fixedCharge\nImport Charge: $importCharge"
                        )
                        details.add(detail)
                    }
                    // Set the new list of details for the adapter
                    adapter.details.clear()
                    adapter.details.addAll(details)
                    // Notify the adapter that the data set has changed
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Database cleared", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    // Display an error message
                    Toast.makeText(this, "Error clearing database", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            builder.setNegativeButton("No") { _, _ ->

            }
            builder.show()
        }

        // Set the adapter for the RecyclerView
        adapter = DetailAdapter(details)
        recyclerView.adapter = adapter

        // Register the RecyclerView for the context menu
        registerForContextMenu(recyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        rs.close()
        db.close()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }


    @SuppressLint("Range")
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            if (selectedItemPosition != -1) {
                val detail = adapter.details[selectedItemPosition]
                rs.moveToPosition(selectedItemPosition)
                val id = rs.getString(rs.getColumnIndex("_id"))
                val deletedRows = try {
                    db.delete("COST_CALCULATION", "_id = ?", arrayOf(id))
                } catch (e: SQLException) {
                    e.printStackTrace()
                    -1
                }
                if (deletedRows > 0) {
                    adapter.details.removeAt(selectedItemPosition)
                    adapter.notifyItemRemoved(selectedItemPosition)
                    selectedItemPosition = -1
                    Toast.makeText(this, "Record deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to delete record", Toast.LENGTH_SHORT).show()
                }
            }
            return true
        }
        return super.onContextItemSelected(item)
    }

    inner class DetailAdapter(val details: MutableList<Detail>) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val detail = details[position]
            holder.title.text = detail.title
            holder.description.text = detail.description

            holder.itemView.setOnLongClickListener {
                selectedItemPosition = position
                false
            }
//            holder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
//                menu.add(Menu.NONE, R.id.menu_delete, Menu.NONE, "Delete")
//            }
        }

        override fun getItemCount() = details.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.title)
            val description: TextView = itemView.findViewById(R.id.description)
        }
    }
}

data class Detail(val title: String, val description: String)



//package com.example.electricitysaver
//
//import android.annotation.SuppressLint
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.os.Bundle
//import android.view.ContextMenu
//import android.view.MenuItem
//import android.view.View
//import android.widget.*
//import android.widget.ListView
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.electricitysaver.databaseHelper.CostCalculationDbHelper
//
//class cost_history : AppCompatActivity() {
//
//    lateinit var db : SQLiteDatabase
//    lateinit var rs : Cursor
//    lateinit var adapter : SimpleCursorAdapter
//
//    @SuppressLint("Range")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.cost_history)
//
//
//        var helper = CostCalculationDbHelper(applicationContext)
//        db = helper.readableDatabase
//
//
//        // Get the data from the database
//        rs = db.rawQuery("SELECT * FROM COST_CALCULATION", null)
//
//        //delete whole DB history
//        val clearButton: Button = findViewById(R.id.btn_his_Clear)
//        clearButton.setOnClickListener {
//            // Show a confirmation dialog
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Clear database")
//            builder.setMessage("Are you sure you want to clear the History?")
//            builder.setPositiveButton("Yes") { _, _ ->
//                // Clear the whole database
//                db.execSQL("DELETE FROM COST_CALCULATION")
//                // Update the list view
//                rs.requery()
//                adapter.notifyDataSetChanged()
//            }
//            builder.setNegativeButton("No") { _, _ ->
//                // Do nothing
//            }
//            builder.show()
//        }
//
//        // Convert the data to a list of strings
//        val dataList = mutableListOf<String>()
//        while (rs.moveToNext()) {
//            val date = rs.getString(rs.getColumnIndex("DATE"))
//            val days = rs.getDouble(rs.getColumnIndex("DAYS"))
//            val units = rs.getDouble(rs.getColumnIndex("UNITS"))
//            val totalCost = rs.getDouble(rs.getColumnIndex("TOTAL_COST"))
//            val fixedCharge = rs.getDouble(rs.getColumnIndex("FIXED_CHARGE"))
//            val importCharge = rs.getDouble(rs.getColumnIndex("IMPORT_CHARGE"))
//            // Use the values
//            val dataString = "Date: $date \nDays: $days \t\t Units: $units \nTotal Cost: $totalCost \nFixed Charge: $fixedCharge \nImport Charge: $importCharge"
//            dataList.add(dataString)
//        }
//
//        // Display the data in the ListView
//        val listView: ListView = findViewById(R.id.hisListView)
//        adapter = SimpleCursorAdapter(
//            this,
//            android.R.layout.simple_list_item_2, // Changed to simple_list_item_2 to show two columns
//            rs,
//            arrayOf("DATE", "TOTAL_COST"), // Include both "DATE" and "TOTAL_COST" columns
//            intArrayOf(android.R.id.text1, android.R.id.text2), // Map the columns to text1 and text2
//            0
//        )
//        listView.adapter = adapter
//
//
//        // Register the ListView for context menu
//        registerForContextMenu(listView)
//
//        // Set the click listener for each item to show the full details
//        listView.setOnItemClickListener { parent, view, position, id ->
//            rs.moveToPosition(position)
//            val date = rs.getString(rs.getColumnIndex("DATE"))
//            val days = rs.getDouble(rs.getColumnIndex("DAYS"))
//            val units = rs.getDouble(rs.getColumnIndex("UNITS"))
//            val totalCost = rs.getDouble(rs.getColumnIndex("TOTAL_COST"))
//            val fixedCharge = rs.getDouble(rs.getColumnIndex("FIXED_CHARGE"))
//            val importCharge = rs.getDouble(rs.getColumnIndex("IMPORT_CHARGE"))
//            // Use the values
//            val dataString = "Date: $date \nDays: $days \t\t Units: $units \nTotal Cost: $totalCost \nFixed Charge: $fixedCharge \nImport Charge: $importCharge"
//            AlertDialog.Builder(this)
//                .setTitle("Details")
//                .setMessage(dataString)
//                .setPositiveButton("OK", null)
//                .show()
//        }
//    }
//
//    override fun onCreateContextMenu(
//        menu: ContextMenu?,
//        v: View?,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        menu?.add(101,11,1,"DELETE")
//        menu?.setHeaderTitle("Removing Data")
//    }
//
//    @SuppressLint("Range")
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
//        val position = info.position
//        rs.moveToPosition(position)
//
//        if (item.itemId == 11) {
//            db.delete("COST_CALCULATION", "_id = ?", arrayOf(rs.getString(rs.getColumnIndex("_id"))))
//            rs = db.rawQuery("SELECT * FROM COST_CALCULATION", null)
//            adapter.swapCursor(rs)
//            return true
//        }
//        return super.onContextItemSelected(item)
//    }
//}