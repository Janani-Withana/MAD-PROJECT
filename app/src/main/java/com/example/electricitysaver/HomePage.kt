package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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

        val btnPay = findViewById<Button>(R.id.HomeBtnPay)
        btnPay.setOnClickListener {
            val mainIntent = Intent(this@HomePage, pamitha_payment::class.java)
            startActivity(mainIntent)
            //finish()
        }
        val btnAddItems = findViewById<Button>(R.id.HomeAddItems)
        btnAddItems.setOnClickListener {
            val mainIntent = Intent(this@HomePage, userAddCustomItem::class.java)
            startActivity(mainIntent)
            //finish()
        }
        val btnAdminPanel = findViewById<Button>(R.id.btnAdmin)
        btnAdminPanel.setOnClickListener {
            val mainIntent = Intent(this@HomePage, AdminDashboard::class.java)
            startActivity(mainIntent)
            //finish()
        }
    }
}