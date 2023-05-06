package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_pamitha_payment2.*


class pamitha_payment : AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    lateinit var rs: Cursor
    lateinit var adapter: SimpleCursorAdapter

    var isNightModeOn = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pamitha_payment2)


        var helper = paymentHelper(applicationContext)
        db = helper.readableDatabase

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }
        val favourites= findViewById<Button>(R.id.viewFavourites)
        favourites.setOnClickListener {

            val mainIntent = Intent(this@pamitha_payment, Favourites::class.java)
            startActivity(mainIntent)
            //finish()

        }


        AddFav.setOnClickListener {
            val payee = payeeCat.selectedItem.toString()
            val name = ptAccountName.text.toString()
            val account = ActNumber.text.toString()
            val amount = etAmount.text.toString()

            if (payee != "CEYLON ELECTRICITY BOARD[CEB]" && payee != "LANKA ELECTRICITY COMPANY(PVT)LTD[LECO]"){
                Toast.makeText(this, "Please select a valid payee category", Toast.LENGTH_SHORT).show()
            }else if (name.isEmpty()){
                Toast.makeText(this, "Please Enter The Account Holder Name", Toast.LENGTH_SHORT).show()
            }
            else if (account.length != 10) {
            // Display error message if account number does not have exactly 10 digits
            Toast.makeText(this, "Please enter a 10-digit account number", Toast.LENGTH_SHORT).show()
            }
            else if(payee.isEmpty() || name.isEmpty() || account.isEmpty() || amount.isEmpty()) {
                // Display error message if any field is empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else {
                // Insert the record into the database
                val cv = ContentValues()
                cv.put("PAYEE", payee)
                cv.put("NAME", name)
                cv.put("ACCOUNT", account)
                cv.put("AMOUNT", amount)
                db.insert("PAYMENT", null, cv)

                // Display success message
                AlertDialog.Builder(this)
                    .setTitle("Add To Favourite")
                    .setMessage("Record added to Favourite List successfully!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }


        val button = findViewById<Button>(R.id.btnDark)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNightModeOn = false
            button.text = "Enable Dark Mode"
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNightModeOn = true
            button.text = "Disable Dark Mode"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        button.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                button.text = "Enable Dark Mode"
                isNightModeOn = false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isNightModeOn = true
                button.text = "Disable Dark Mode"
            }
        }


    }

}