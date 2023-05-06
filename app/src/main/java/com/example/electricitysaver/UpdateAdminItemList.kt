package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.AdminItemDbHelper

class UpdateAdminItemList : AppCompatActivity() {
    private lateinit var edtCat : EditText
    private lateinit var edtBrand : EditText
    private lateinit var edtWatts : EditText
    private lateinit var btnUpdate : Button

    private lateinit var helper: AdminItemDbHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var rs: Cursor


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_admin_item_list)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        // Retrieve the data from the intent
        val getCategory = intent.getStringExtra("CATEGORY")
        val getBrand = intent.getStringExtra("BRAND")
        val getWatts = intent.getDoubleExtra("WATTS", 0.0)
        val itemId = intent.getLongExtra("ID", -1)

        // Set the data on the relevant EditText elements in the layout
        edtCat = findViewById(R.id.updCat)
        edtBrand = findViewById(R.id.updBrand)
        edtWatts = findViewById(R.id.updWatts)
        btnUpdate = findViewById(R.id.btnItemUpdate)

        if(edtCat!=null){ edtCat.setText(getCategory) }
        if(edtBrand!=null){ edtBrand.setText(getBrand) }
        if(edtWatts!=null){ edtWatts.setText(getWatts.toString()) }

        // Get a reference to the database helper and writable database
        helper = AdminItemDbHelper(this)
        db = helper.writableDatabase
        // Get the record with the specified itemId
        rs = db.rawQuery("SELECT _id, CATEGORY, BRAND, WATTS FROM Admin_Add_Item WHERE _id = ?", arrayOf(itemId.toString()))

        //Update Item
        btnUpdate.setOnClickListener {
            val category = edtCat.text.toString()
            val brand = edtBrand.text.toString()
            val watts = edtWatts.text.toString().toDouble()

            // Update the record with the new data
            val values = ContentValues().apply {
                put("CATEGORY", category)
                put("BRAND", brand)
                put("WATTS", watts)
            }
            val selection = "_id = ?"
            val selectionArgs = arrayOf(itemId.toString())

            val count = db.update("Admin_Add_Item", values, selection, selectionArgs)

            if (count > 0) {
                // Show a success message and finish the activity
                var ad = AlertDialog.Builder(this)
                ad.setTitle("Update Record")
                ad.setMessage("Record Updated Successfully....")
                ad.setPositiveButton("OK",DialogInterface.OnClickListener{ dialogInterface, i ->
                    edtCat.setText(rs.getString(1))
                    edtBrand.setText(rs.getString(2))
                    edtWatts.setText(rs.getString(3))
                })
                ad.show()
            }else {
                // Show an error message
                 AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Failed to update record")
                    .setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                .show()
            }
        }

    }
}