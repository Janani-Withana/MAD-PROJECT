package com.example.electricitysaver

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.UserItemDbHelper
import java.math.RoundingMode
import java.text.DecimalFormat

class ListView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter
    private lateinit var totalUnit:TextView
    private lateinit var exUnit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        totalUnit = findViewById(R.id.totalUnit)
        exUnit = findViewById(R.id.exUnit)


        var helper = UserItemDbHelper(applicationContext)
        var db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM USER_ADD_ITEM", null)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val recyclerView: RecyclerView = findViewById(R.id.itemreview)
        adapter = ListAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Map the cursor to a list of items and calculate the expected values
        val items = ArrayList<ListItem>()
        var unit = 0.0


        ////////////////////////////////////
        while (rs.moveToNext()) {
            var id = rs.getString(0).toInt()
            var category = rs.getString(1)
            var duration = rs.getString(5).toDouble()
            var watt = rs.getString(6).toDouble()
            var qty = rs.getString(3).toDouble()

            // Add the item to the list
            items.add(ListItem(category, duration.toString(),id))

            // Calculate monthly unit
            var munit = calculateMonthlyUnit(duration, watt, qty)

            // Update total unit
            unit += munit
        }
        rs.close()
        totalUnit.text = unit.toString()
        adapter.setItems(items)
        adapter.notifyDataSetChanged()


        val btnExpectListView = findViewById<Button>(R.id.submit_btn)
        btnExpectListView.setOnClickListener {

            var exunit = 0.00
            var exunit1 = exUnit.text.toString()
            if (exunit1.matches(Regex("\\d+"))) {
                exunit = exunit1.toDouble()
                // Use the userInt variable for any necessary operations
            }

            //var exunit = 100.0
            Log.e("Total units",unit.toString())
            var helper = UserItemDbHelper(applicationContext)
            var db = helper.readableDatabase
            val rs = db.rawQuery("SELECT * FROM USER_ADD_ITEM", null)


            while (rs.moveToNext()) {

                var category = rs.getString(1)
                var duration = rs.getString(5).toDouble()
                var watt = rs.getString(6).toDouble()
                var qty = rs.getString(3).toDouble()



                // Calculate monthly unit
                var munit = calculateMonthlyUnit(duration, watt, qty)


                // Calculate expected unit amounts
                var MExUnit = calculateMonthlyExpectedUnitAmount(munit, exunit, unit)
                var DExUnit = calculateDailyExpectedUnitAmount(MExUnit)

                // Calculate expected daily and monthly hours



                var ExDailyHours = calculateExpectedDailyHours(DExUnit, watt)
                var ExMonthlyHours = calculateExpectedMonthlyHours(ExDailyHours)
                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.CEILING
                var formattedExDailyHours = df.format(ExDailyHours)
                var formattedExMonthlyHours = df.format(ExMonthlyHours)


                Log.d("TAG7", ExDailyHours.toString())
                Log.d("TAG8", ExMonthlyHours.toString())


                // Insert data to the database
                var cv = ContentValues().apply {
                    put("CATEGORY", category)
                    put("DAYLYUSE", formattedExDailyHours.toString())
                    put("MONTHLYUSE", formattedExMonthlyHours.toString())
                }
                db.insert("USER_EXPECTED_ITEM", null, cv)


            }
            rs.close()


            val mainIntent = Intent(this, expectedUnitList::class.java)
            startActivity(mainIntent)
            finish()

        }
    }
    // Define a function to calculate monthly unit
    private fun calculateMonthlyUnit(duration: Double, watt: Double, qty: Double): Double {
        var month = duration
        var watt = watt * qty
        Log.d("TAG Monthly Usage Hours", month.toString())
        Log.d("TAG1 Item watte", watt.toString())
        Log.d("TAG2 Total Monthly unit", ((month * watt) / 1000).toString())
        return (month * watt) / 1000
    }

    // Define a function to calculate monthly expected unit amount
    private fun calculateMonthlyExpectedUnitAmount(munit: Double, exunit: Double, unit: Double): Double {
        Log.d("expectedmonthlyunit", ((munit * exunit) / unit).toString())
        return (munit * exunit) / unit
    }

    // Define a function to calculate daily expected unit amount
    private fun calculateDailyExpectedUnitAmount(MExUnit: Double): Double {
        Log.d("dailyExpectedUnitAmount", (MExUnit / 30).toString())
        return MExUnit / 30
    }

    // Define a function to calculate expected daily hours
    private fun calculateExpectedDailyHours(DExUnit: Double, watt: Double): Double {
        Log.d("ExpectedDailyHours", ((DExUnit * 1000) / watt).toString())
        return (DExUnit * 1000) / watt
    }

    // Define a function to calculate expected monthly hours
    private fun calculateExpectedMonthlyHours(ExDailyHours: Double): Double {
        Log.d("ExpectedMonthlyHours", (ExDailyHours * 30).toString())
        return ExDailyHours * 30
    }



}