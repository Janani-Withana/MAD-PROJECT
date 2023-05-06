package com.example.electricitysaver

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.Typeface
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
    lateinit var edtCharge: EditText
    private lateinit var btnUpdate: Button

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cost_range)

        var dbHelper = CalculationRatesDbHelper(applicationContext)
        db = dbHelper.readableDatabase

        // Find the button in your layout
        val viewListButton: Button = findViewById(R.id.btnViewList)
        val categorySpinner: Spinner = findViewById(R.id.category_spinner)
        val rangeSpinner: Spinner = findViewById(R.id.range_spinner)
        edtCharge = findViewById(R.id.edtCharge)
        btnUpdate =findViewById(R.id.btnUpdate)

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
                Toast.makeText(this, "Error retrieving data for all: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Set an OnItemSelectedListener on both spinners to update the value of edtCharge
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    val selectedCategory = categorySpinner.selectedItem.toString()
                    val selectedRange = rangeSpinner.selectedItem.toString()
                    val cursor = db.rawQuery("SELECT $selectedRange FROM RATE_TABLE WHERE CATEGORY = ?", arrayOf(selectedCategory))
                    if (cursor.moveToFirst()) {
                        val rate = cursor.getFloat(0)
                        edtCharge.setText(rate.toString())
                        edtCharge.setTypeface(null, Typeface.BOLD)
                    }
                    cursor.close()
                } catch (e: Exception) {
                    // Show an error message if there was an error retrieving the data
                    //Toast.makeText(this@CostRange, "Error retrieving data category spinner: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        rangeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    val selectedCategory = categorySpinner.selectedItem.toString()
                    val selectedRange = rangeSpinner.selectedItem.toString()
                    val cursor = db.rawQuery("SELECT $selectedRange FROM RATE_TABLE WHERE CATEGORY = ?", arrayOf(selectedCategory))
                    if (cursor.moveToFirst()) {
                        val rate = cursor.getFloat(0)
                        edtCharge.setText(rate.toString())
                        edtCharge.setTypeface(null, Typeface.BOLD)
                    }
                    cursor.close()
                } catch (e: Exception) {
                    // Show an error message if there was an error retrieving the data
                    //Toast.makeText(this@CostRange, "Error retrieving data range spinner: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnUpdate.setOnClickListener {
            try {
                val selectedCategory = categorySpinner.selectedItem.toString()
                val selectedRange = rangeSpinner.selectedItem.toString()
                val newCharge = edtCharge.text.toString().toFloat()
                db.execSQL("UPDATE RATE_TABLE SET $selectedRange = $newCharge WHERE CATEGORY = ?", arrayOf(selectedCategory))
                Toast.makeText(this@CostRange, "Charge updated successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@CostRange, "Error updating charge: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}