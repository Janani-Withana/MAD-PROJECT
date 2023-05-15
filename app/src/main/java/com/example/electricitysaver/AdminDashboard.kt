package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog

class AdminDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val btnHomePage = findViewById<ImageView>(R.id.HomeAdmin)
        btnHomePage.setOnClickListener {
            val mainIntent = Intent(this, AdminDashboard::class.java)
            startActivity(mainIntent)
            //finish()
        }

        //View Users Count
        val btnViewUsers = findViewById<ImageButton>(R.id.AdminUsers)
        btnViewUsers.setOnClickListener {

            val bottomSheetDialogFragment = ViewUsers()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)

        }

        //Cost Range
        val consumptionCost= findViewById<ImageButton>(R.id.btnCostCal)
        consumptionCost.setOnClickListener {
            val mainIntent = Intent(this, CostRange::class.java)
            startActivity(mainIntent)
            //finish()
        }

        //Admin Add Device
        val btnAdminAddDevices= findViewById<ImageButton>(R.id.AdminAddDevice)
        btnAdminAddDevices.setOnClickListener {
            val mainIntent = Intent(this, AdminAddDevices::class.java)
            startActivity(mainIntent)
            //finish()
        }

        //Device List
        val btnAdminPanel = findViewById<ImageButton>(R.id.AdminDeviceList)
        btnAdminPanel.setOnClickListener {
            val mainIntent = Intent(this, AdminListView::class.java)
            startActivity(mainIntent)
            //finish()
        }
    }
}