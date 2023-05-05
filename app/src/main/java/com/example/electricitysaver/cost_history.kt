package com.example.electricitysaver

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class cost_history : AppCompatActivity() {
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cost_history)
    }

}