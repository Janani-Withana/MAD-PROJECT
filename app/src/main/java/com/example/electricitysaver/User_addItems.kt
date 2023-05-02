package com.example.electricitysaver

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.UserItemDbHelper
import java.util.Locale.Category

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
        ccat = findViewById(R.id.ccat)
        brand = findViewById(R.id.brand)
        qty = findViewById(R.id.qty)
        du = findViewById(R.id.du)
        mu = findViewById(R.id.mu)
        nw = findViewById(R.id.nw)

        btnCusAdd.setOnClickListener{
            var cv = ContentValues()
            cv.put("CATEGORY",ccat.text.toString())
            cv.put("BRAND",brand.text.toString())
            cv.put("QTY",qty.text.toString().toInt())
            cv.put("DAYUSE",du.text.toString().toInt())
            cv.put("MONUSE",mu.text.toString().toInt())
            cv.put("NOW",nw.text.toString().toInt())
            db.insert("USER_ADD_ITEM",null,cv)
            rs.requery()

            //Show pop up box
            var ad = AlertDialog.Builder(this)
            ad.setTitle("Add record")
            ad.setMessage("Record Inserted Successfully....!")
            ad.setPositiveButton("OK", DialogInterface.OnClickListener{ DialogInterface, i ->
                ccat.setText("")
                brand.setText("")
                brand.setText("")
                qty.setText("")
                du.setText("")
                mu.setText("")
                nw.setText("")

                ccat.requestFocus()
            })
            ad.show()

        }




        val buttonList = findViewById<Button>(R.id.btnlist)
        buttonList.setOnClickListener {
            val intent = Intent(this@User_addItems, ListView::class.java)
            startActivity(intent)
        }
    }
}
