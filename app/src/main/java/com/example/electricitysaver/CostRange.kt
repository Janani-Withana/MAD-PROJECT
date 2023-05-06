package com.example.electricitysaver

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.CalculationRatesDbHelper
import com.example.electricitysaver.databaseHelper.CostCalculationDbHelper
import kotlinx.android.synthetic.main.activity_cost_range.*

class CostRange : AppCompatActivity() {

    lateinit var db : SQLiteDatabase
    lateinit var rs : Cursor
    lateinit var adapter : SimpleCursorAdapter

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cost_range)

        var dbHelper = CalculationRatesDbHelper(applicationContext)
        db = dbHelper.readableDatabase

        // Find the button in your layout
        val viewListButton: Button = findViewById(R.id.btnViewList)
        val categorySpinner: Spinner = findViewById(R.id.category_spinner)

//        CATEGORY
//        BLOCK1_RATE
//        BLOCK2_RATE
//        BLOCK3_RATE
//        BLOCK4_RATE
//        BLOCK5_RATE


        // Set a click listener on the button
        viewListButton.setOnClickListener {
            try {
                // Query the database to retrieve the data
                val dbHelper = CalculationRatesDbHelper(this)
                val db = dbHelper.readableDatabase
                val cursor = db.query("RATE_TABLE", null, null, null, null, null, null)

                // Create a StringBuilder to hold the data
                val dataBuilder = StringBuilder()
                cursor.use {
                    while (it.moveToNext()) {
                        val category = it.getString(it.getColumnIndex("CATEGORY"))
                        val block1Rate = it.getFloat(it.getColumnIndex("BLOCK1_RATE"))
                        val block2Rate = it.getFloat(it.getColumnIndex("BLOCK2_RATE"))
                        val block3Rate = it.getFloat(it.getColumnIndex("BLOCK3_RATE"))
                        val block4Rate = it.getFloat(it.getColumnIndex("BLOCK4_RATE"))
                        val block5Rate = it.getFloat(it.getColumnIndex("BLOCK5_RATE"))
                        dataBuilder.append("$category: $block1Rate, $block2Rate, $block3Rate, $block4Rate, $block5Rate\n")
                    }
                }

                // Show an AlertDialog with the retrieved data
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder
                    .setTitle("Rate Table")
                    .setMessage(dataBuilder.toString())
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            } catch (e: Exception) {
                // Show an error message if there was an error retrieving the data
                Toast.makeText(this, "Error retrieving data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }


    }
}