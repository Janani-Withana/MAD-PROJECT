package com.example.electricitysaver

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.AdminItemDbHelper


class AdminAddDevices : AppCompatActivity() {
    private lateinit var cat : EditText
    private lateinit var brand : EditText
    private lateinit var watts : EditText
    private lateinit var btnAdd : Button

    private lateinit var helper: AdminItemDbHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var rs: Cursor
    private lateinit var adapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_add_devices)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val btnAdminPage = findViewById<ImageView>(R.id.HomeAdmin)
        btnAdminPage.setOnClickListener {
            val mainIntent = Intent(this, AdminDashboard::class.java)
            startActivity(mainIntent)
            //finish()
        }

        cat = findViewById(R.id.cat)
        brand = findViewById(R.id.brand)
        watts = findViewById(R.id.edtWatts)
        btnAdd = findViewById(R.id.btnAdminAdd)

        helper = AdminItemDbHelper(applicationContext)
        db = helper.readableDatabase
        rs = db.rawQuery("SELECT * FROM Admin_Add_Item", null)

        //Add Item
        btnAdd.setOnClickListener {

            // Validate input fields
            val wattsValue = watts.text.toString().toDoubleOrNull()
            // Display error message if any field is empty
            if (cat.text.isEmpty() && brand.text.isEmpty() && watts.text.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if(cat.text.isEmpty()) {
                cat.error = "Category cannot be empty"
                return@setOnClickListener
            } else if (brand.text.isEmpty()) {
                brand.error = "Brand cannot be empty"
                return@setOnClickListener
            }
            else if (watts.text.isEmpty()) {
                watts.error = "No of watts cannot be empty"
                return@setOnClickListener
            }
              else if (wattsValue == null || wattsValue <= 0) {
                watts.error = "Watts must be a positive number"
                return@setOnClickListener
            }
              else{

                var cv = ContentValues()
                cv.put("CATEGORY", cat.text.toString())
                cv.put("BRAND", brand.text.toString())
                cv.put("WATTS", watts.text.toString().toDouble())
                db.insert("Admin_Add_Item", null, cv)
                rs.requery()

                var ad = AlertDialog.Builder(this)
                ad.setTitle("Add Record")
                ad.setMessage("Record Inserted Successfully....")
                ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    cat.setText("")
                    brand.setText("")
                    watts.setText("")
                    cat.requestFocus()
                })
                ad.show()
            }
        }

        val btnList = findViewById<Button>(R.id.btnAdminViewList)
        btnList.setOnClickListener {
            val intent = Intent(this, AdminListView::class.java)
            startActivity(intent)
        }

    }


}
