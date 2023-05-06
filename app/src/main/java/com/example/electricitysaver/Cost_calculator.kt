package com.example.electricitysaver

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.electricitysaver.databaseHelper.CostCalculationDbHelper


class Cost_calculator : AppCompatActivity() {

    lateinit var db : SQLiteDatabase
    lateinit var rs : Cursor
    lateinit var adapter : SimpleCursorAdapter

    private lateinit var edtCurrReading: EditText
    private lateinit var edtPreReading: EditText
    private lateinit var edtUnits: EditText
    private lateinit var tvCurrReading : TextView
    private lateinit var tvPreReading : TextView
    private lateinit var tvUnits3: TextView
    private  lateinit var tvDisplayCost: TextView

    private lateinit var edtEDate: TextView
    private lateinit var edtSDate: TextView
    private lateinit var tvCalDate: TextView

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    private val DATE_FORMAT = "yyyy-MM-dd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cost_calculator)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        var helper = CostCalculationDbHelper(applicationContext)
        db = helper.readableDatabase

        // Get the current date
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)

        val historyButton = findViewById<TextView>(R.id.btnViewHistory)
        historyButton.setOnClickListener {
            val intent = Intent(this, cost_history::class.java)
            startActivity(intent)
        }

        edtCurrReading = findViewById(R.id.edtCurrReading)
        edtPreReading = findViewById(R.id.edtPreReading)
        edtUnits = findViewById(R.id.edtUnits)
        edtEDate = findViewById(R.id.edtEDate)
        edtSDate = findViewById(R.id.edtSDate)
        tvCalDate = findViewById(R.id.tvCalDate)
        tvDisplayCost = findViewById(R.id.tvDisplayCost)

        tvCurrReading = findViewById(R.id.tvCurrReading)
        tvPreReading = findViewById(R.id.tvPreReading)
        tvUnits3 = findViewById(R.id.tvUnits3)

        //disabling part for radio buttons
        val readingRadioButton = findViewById<RadioButton>(R.id.radioButton3)
        val unitRadioButton = findViewById<RadioButton>(R.id.radioButton)

        readingRadioButton.setOnCheckedChangeListener(object : OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    edtUnits.isEnabled = false
                    edtCurrReading.isEnabled = true
                    edtPreReading.isEnabled = true
                    edtPreReading.setText("")
                    edtCurrReading.setText("")
                    edtUnits.setText("")
                    tvUnits3.setTextColor(Color.parseColor("#616161"))
                    tvCurrReading.setTextColor(Color.parseColor("#000000"))
                    tvPreReading.setTextColor(Color.parseColor("#000000"))
                    tvUnits3.setTypeface(null, Typeface.NORMAL)
                    tvCurrReading.setTypeface(null, Typeface.BOLD)
                    tvPreReading.setTypeface(null, Typeface.BOLD)
                }
            }
        })

        unitRadioButton.setOnCheckedChangeListener(object : OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    edtUnits.isEnabled = true
                    edtCurrReading.isEnabled = false
                    edtPreReading.isEnabled = false
                    edtPreReading.setText("")
                    edtCurrReading.setText("")
                    edtUnits.setText("")
                    tvCurrReading.setTextColor(Color.parseColor("#616161"))
                    tvPreReading.setTextColor(Color.parseColor("#616161"))
                    tvUnits3.setTextColor(Color.parseColor("#000000"))
                    tvUnits3.setTypeface(null, Typeface.BOLD)
                    tvCurrReading.setTypeface(null, Typeface.NORMAL)
                    tvPreReading.setTypeface(null, Typeface.NORMAL)
                }
            }
        })

        // Add a click listener to the Calculate button
        btnCalculate.setOnClickListener {
            val preReading = edtPreReading.text.toString().toDoubleOrNull()
            val currReading = edtCurrReading.text.toString().toDoubleOrNull()
            val unitsInput = edtUnits.text.toString().toDoubleOrNull()

            if ((preReading != null && currReading != null) || unitsInput != null) {
                val units = unitsInput ?: currReading!! - preReading!!
                if (units >= 0) {
                    edtUnits.setText(units.toString())
                    val days = tvCalDate.text.toString().toDoubleOrNull() ?: 0.0
                    val (fixedCharge, importCharge, totalCost) = calculateElectricityCost(days, units)
                    val formattedCost = "Rs. %.2f".format(totalCost)
                    tvDisplayCost.text = formattedCost

                    try {
                        //insert records
                        var cv = ContentValues()
                        cv.put("DATE","$currentDate")
                        cv.put("DAYS" , days)
                        cv.put("UNITS" , units)
                        cv.put("TOTAL_COST" ,totalCost )
                        cv.put("FIXED_CHARGE" ,fixedCharge)
                        cv.put("IMPORT_CHARGE" ,importCharge)
                        db.insert("COST_CALCULATION", null, cv)

                        // Use the values
                        val dataString = "Date: $currentDate \nDays: $days \t\t Units: $units \nTotal Cost: $totalCost \nFixed Charge: $fixedCharge \nImport Charge: $importCharge"
                        AlertDialog.Builder(this)
                            .setTitle("Details")
                            .setMessage(dataString)
                            .setPositiveButton("OK", null)
                            .show()
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error inserting record into database: ${e.message}", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    edtUnits.setText("")
                    Toast.makeText(this, "Current reading cannot be less than previous reading", Toast.LENGTH_SHORT).show()
                }
            } else {
                edtUnits.setText("")
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            }
        }



        // Add TextChangedListeners to both EditText fields
        edtPreReading.addTextChangedListener {
            calculateUnits()
        }

        edtCurrReading.addTextChangedListener {
            calculateUnits()
        }

        //setting dates to calculate time periods
        edtSDate.setOnClickListener {
            val currentDate = if (startDate != null) startDate else Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    startDate = Calendar.getInstance()
                    startDate?.set(year, monthOfYear, dayOfMonth)

                    if (endDate != null && startDate?.after(endDate) == true) {
                        Toast.makeText(this, "Please select a start date before the end date", Toast.LENGTH_SHORT).show()
                        startDate = null
                    } else {
                        updateStartDateText()
                        updateNumberOfDays()
                    }
                },
                currentDate?.get(Calendar.YEAR) ?: Calendar.getInstance().get(Calendar.YEAR),
                currentDate?.get(Calendar.MONTH) ?: Calendar.getInstance().get(Calendar.MONTH),
                currentDate?.get(Calendar.DAY_OF_MONTH) ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        edtEDate.setOnClickListener {
            val currentDate = if (endDate != null) endDate else Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    endDate = Calendar.getInstance()
                    endDate?.set(year, monthOfYear, dayOfMonth)

                    if (startDate != null && startDate?.after(endDate) == true) {
                        Toast.makeText(this, "Please select an end date after the start date", Toast.LENGTH_SHORT).show()
                        endDate = null
                    } else {
                        updateEndDateText()
                        updateNumberOfDays()
                    }
                },
                currentDate?.get(Calendar.YEAR) ?: Calendar.getInstance().get(Calendar.YEAR),
                currentDate?.get(Calendar.MONTH) ?: Calendar.getInstance().get(Calendar.MONTH),
                currentDate?.get(Calendar.DAY_OF_MONTH) ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }
    }

    // Define a function to calculate units
    private fun calculateUnits() {
        val preReading = edtPreReading.text.toString().toDoubleOrNull()
        val currReading = edtCurrReading.text.toString().toDoubleOrNull()

        if (preReading != null && currReading != null) {
            val units = currReading - preReading
            if (units >= 0) {
                edtUnits.setText(units.toString())
            } else {
                edtUnits.setText("")
            }
        } else {
            // You may also want to clear the units field if the inputs are invalid
            edtUnits.setText("")
        }
    }

    private fun updateStartDateText() {
        val formattedDate = startDate?.let { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.time) }
        //imageView3.visibility = View.GONE
        formattedDate?.let { edtSDate.setText(it) }
    }

    private fun updateEndDateText() {
        val formattedDate = endDate?.let { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.time) }
        //imageView7.visibility = View.GONE
        formattedDate?.let { edtEDate.setText(it) }
    }

    private fun updateNumberOfDays() {
        if (startDate != null && endDate != null) {
            val days = TimeUnit.MILLISECONDS.toDays(endDate!!.timeInMillis - startDate!!.timeInMillis)
            tvCalDate.setText(days.toString())
        } else {
            tvCalDate.setText("")
        }
    }

    fun calculateElectricityCost(days: Double, units: Double): Triple<Double, Double, Double> {
        val fixedRate: Double = if (units / (days / 30.0) in 0.0..60.0) {
            650.0
        } else if (units / (days / 30.0) in 61.0..90.0) {
            650.0
        } else if (units / (days / 30.0) in 91.0..120.0) {
            1500.0
        } else if (units / (days / 30.0) in 121.0..180.0) {
            1500.0
        } else {
            2000.0
        }

        val fixedCharge: Double = if (days < 54.0) {
            fixedRate
        } else {
            fixedRate * (days / 30.0)
        }

        if (days == 0.0) {
            return Triple(fixedCharge, 0.0, 0.0)
        }

        val block1Rate: Double = 42.00
        val block2Rate: Double = 42.00
        val block3Rate: Double = 50.00
        val block4Rate: Double = 50.00
        val block5Rate: Double = 75.00

        val block1Units: Double = days * 2.0
        val block2Units: Double = days
        val block3Units: Double = days
        val block4Units: Double = days * 2.0
        val block5Units: Double = 0.0

        var remainingUnits: Double = units
        var cost: Double = 0.0

        if(units <= 60){
            // Calculate cost for block 1
            val block1: Double = minOf(block1Units, remainingUnits)
            remainingUnits -= block1
            cost += block1 * block1Rate

            // Calculate cost for block 2
            val block2: Double = minOf(block2Units, remainingUnits)
            remainingUnits -= block2
            cost += block2 * block2Rate

            // Add fixed charge
            cost += fixedCharge
        }else {
            // Calculate cost for block 1
            val block1: Double = minOf(block1Units, remainingUnits)
            remainingUnits -= block1
            cost += block1 * block1Rate

            // Calculate cost for block 2
            val block2: Double = minOf(block2Units, remainingUnits)
            remainingUnits -= block2
            cost += block2 * block2Rate

            // Calculate cost for block 3
            val block3: Double = minOf(block3Units, remainingUnits)
            remainingUnits -= block3
            cost += block3 * block3Rate

            // Calculate cost for block 4
            val block4: Double = minOf(block4Units, remainingUnits)
            remainingUnits -= block4
            cost += block4 * block4Rate

            // Calculate cost for block 5
            val block5: Double = remainingUnits
            cost += block5 * block5Rate

            // Add fixed charge
            cost += fixedCharge
        }

        var importCharge = if(days == 0.0){
            fixedCharge
        }else{
            cost - fixedCharge
        }

        return Triple(fixedCharge, importCharge, cost)
    }

}

