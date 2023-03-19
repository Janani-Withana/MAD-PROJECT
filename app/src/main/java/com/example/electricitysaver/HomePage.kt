package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class HomePage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

       val btnPay = findViewById<ImageButton>(R.id.paybills)
        btnPay.setOnClickListener {
            val mainIntent = Intent(this@HomePage, pamitha_payment::class.java)
            startActivity(mainIntent)
            //finish()
        }
        val btnAddItems = findViewById<ImageButton>(R.id.btnUser)
        btnAddItems.setOnClickListener {
            val mainIntent = Intent(this@HomePage, expectedUnitList::class.java)
            startActivity(mainIntent)
            //finish()
        }
        val btnAdminPanel = findViewById<ImageButton>(R.id.listdevice)
        btnAdminPanel.setOnClickListener {
            val mainIntent = Intent(this@HomePage, AdminDashboard::class.java)
            startActivity(mainIntent)
            //finish()
        }


    }
}