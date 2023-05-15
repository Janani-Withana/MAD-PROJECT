package com.example.electricitysaver

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class UserUpdateEditItems : AppCompatActivity() {
    private lateinit var ucat: EditText
    private lateinit var ubrand: EditText
    private lateinit var uqty: EditText
    private lateinit var udu: EditText
    private lateinit var umu: EditText
    private lateinit var unw: EditText
    private lateinit var btnupdate: Button
    private lateinit var btnulist: Button
    private var itemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_update_edit_items)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        ucat = findViewById(R.id.ucat)
        ubrand = findViewById(R.id.ubrand)
        uqty = findViewById(R.id.uqty)
        udu = findViewById(R.id.udu)
        umu = findViewById(R.id.umu)
        unw = findViewById(R.id.unw)
        btnupdate = findViewById(R.id.btnupdate)
        btnulist = findViewById(R.id.btnulist)

        itemId = intent.getStringExtra("ID")
        val id = itemId.toString().toInt()
        Log.e("ID check",itemId.toString())

        val helper = UserItemDbHelper(applicationContext)
        val db = helper.readableDatabase
        val rs1 = db.rawQuery("SELECT * FROM USER_ADD_ITEM", null)

        // Use a try-catch block to handle exceptions when querying the database
        try {
            val rs = db.rawQuery("SELECT * FROM USER_ADD_ITEM WHERE _id == $id", null)
            if (rs.moveToFirst()) {
                ucat.setText(rs.getString(rs.getColumnIndexOrThrow("CATEGORY")))
                ubrand.setText(rs.getString(rs.getColumnIndexOrThrow("BRAND")))
                uqty.setText(rs.getString(rs.getColumnIndexOrThrow("QTY")))
                udu.setText(rs.getString(rs.getColumnIndexOrThrow("DAYUSE")))
                umu.setText(rs.getString(rs.getColumnIndexOrThrow("MONUSE")))
                unw.setText(rs.getString(rs.getColumnIndexOrThrow("NOW")))
            }
            rs.close()
        } catch (e: Exception) {
            Log.e("UserUpdateEditItems", "Error querying database: ${e.message}")
        }

        btnupdate.setOnClickListener {
            var cv = ContentValues()
            cv.put("CATEGORY",ucat.text.toString())
            cv.put("BRAND",ubrand.text.toString())
            cv.put("QTY",uqty.text.toString().toInt())
            cv.put("DAYUSE",udu.text.toString().toInt())
            cv.put("MONUSE",umu.text.toString().toInt())
            cv.put("NOW",unw.text.toString().toInt())
            val rowsAffected = db.update("USER_ADD_ITEM", cv, "_id = ?", arrayOf(id.toString()))
            if (rowsAffected > 0) {
                val alertDialog = AlertDialog.Builder(this).apply {
                    setTitle("Update record")
                    setMessage("Record updated successfully")
                    setPositiveButton("OK") { _, _ ->
                        // do something after the user clicks the OK button
                    }
                }
                alertDialog.show()
            }else{
                Toast.makeText(this, "Update Not successful", Toast.LENGTH_SHORT).show()
            }


        }

        btnulist.setOnClickListener {
            val intent = Intent(this, ListView::class.java)
            startActivity(intent)
        }
    }
}
