package com.example.electricitysaver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.electricitysaver.databaseHelper.AdminItemDbHelper
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class AdminAddDevices : AppCompatActivity() {

    private lateinit var cat : EditText
    private lateinit var brand : EditText
    private lateinit var watts : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_add_devices_bottomsheet)

        var helper = AdminItemDbHelper(applicationContext)
        var db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM Admin_Add_Item", null)

//        cat = findViewById(R.id.)
//        brand = findViewById(R.id.)
//        watts = findViewById(R.id.)

    }
}