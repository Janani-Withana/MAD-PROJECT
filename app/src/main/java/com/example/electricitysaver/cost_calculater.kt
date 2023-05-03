package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class cost_calculater : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cost_calculator)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        //see history
        val btnSeeHistory = findViewById<Button>(R.id.btnViewHistory)
        btnSeeHistory.setOnClickListener {
            val mainIntent = Intent(this, cost_history::class.java)
            startActivity(mainIntent)
            //finish()
        }
    }
}