package com.example.electricitysaver

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class cost_calculator : AppCompatActivity() {

    private lateinit var edtCurrReading: EditText
    private lateinit var edtPreReading: EditText
    private lateinit var edtUnits: EditText

    private lateinit var edtEDate: TextView
    private lateinit var edtSDate: TextView

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    private val DATE_FORMAT = "yyyy-MM-dd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cost_calculator)

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

        btnCalculate.setOnClickListener {
            val preReading = edtPreReading.text.toString().toDoubleOrNull()
            val currReading = edtCurrReading.text.toString().toDoubleOrNull()

            if (preReading != null && currReading != null) {
                val units = currReading - preReading
                edtUnits.setText(units.toString())
            } else {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            }
        }

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
            edtUnits.setText(days.toString())
        } else {
            edtUnits.setText("")
        }
    }
}
