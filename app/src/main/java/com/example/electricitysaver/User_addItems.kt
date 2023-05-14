package com.example.electricitysaver

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class User_addItems : AppCompatActivity() {

    private lateinit var ccat : EditText
    private lateinit var brand : EditText
    private lateinit var qty : EditText
    private lateinit var du : EditText
    private lateinit var mu : EditText
    private lateinit var nw : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add_items)

        var helper = UserItemDbHelper(applicationContext)
        var db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM USER_ADD_ITEM", null)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val btnCusAdd = findViewById<Button>(R.id.btnCusAdd)
        ccat = findViewById(R.id.cat)
        brand = findViewById(R.id.brand)
        qty = findViewById(R.id.qty)
        du = findViewById(R.id.du)
        mu = findViewById(R.id.mu)
        nw = findViewById(R.id.nw)

        btnCusAdd.setOnClickListener{
            val category = ccat.text.toString().trim()
            val brandName = brand.text.toString().trim()
            val quantity = qty.text.toString().trim()
            val dayUse = du.text.toString().trim()
            val monthUse = mu.text.toString().trim()
            val nowValue = nw.text.toString().trim()

            // Validate input fields
            if (category.isEmpty() || brandName.isEmpty() || quantity.isEmpty() || dayUse.isEmpty() || monthUse.isEmpty() || nowValue.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (quantity.toInt() <= 0 ) {
                qty.error = "Quantity must be greater than 0"
                return@setOnClickListener
            }
            if(dayUse.toInt() <= 0 || dayUse.toInt() > 24){
                du.error = "Day use must be greater than 0 and less than 24"
                return@setOnClickListener
            }
            if(monthUse.toInt() <= 0 || monthUse.toInt() > 720){
                mu.error = "Month use must be greater than 0 and less than 720"
                return@setOnClickListener
            }

            val cv = ContentValues().apply {
                put("CATEGORY", category)
                put("BRAND", brandName)
                put("QTY", quantity.toInt())
                put("DAYUSE", dayUse.toInt())
                put("MONUSE", monthUse.toInt())
                put("NOW", nowValue.toInt())
            }
            val rowsAffected = db.insert("USER_ADD_ITEM", null, cv)
            rs.requery()

            if(rowsAffected > 0 ) {
                //Show pop up box
                val ad = AlertDialog.Builder(this)
                ad.setTitle("Add record")
                ad.setMessage("Record Inserted Successfully....!")
                ad.setPositiveButton("OK") { DialogInterface, i ->
                    ccat.setText("")
                    brand.setText("")
                    brand.setText("")
                    qty.setText("")
                    du.setText("")
                    mu.setText("")
                    nw.setText("")
                    ccat.requestFocus()
                }
                ad.show()

            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }


        }

        val buttonList = findViewById<Button>(R.id.btnlist)
        buttonList.setOnClickListener {
            val intent = Intent(this@User_addItems, ListView::class.java)
            startActivity(intent)
        }
    }
}
