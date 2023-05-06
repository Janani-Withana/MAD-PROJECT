package com.example.electricitysaver

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.electricitysaver.databaseHelper.UserItemDbHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar and set navigation bar color
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.parseColor("#133B5C")
        }

        // Initialize views and database helper
        edtEmail = findViewById(R.id.edt_Email)
        edtPassword = findViewById(R.id.edt_Password)
        db = UserItemDbHelper(applicationContext).readableDatabase


        // Set click listener for sign up link
        val tvSignUp = findViewById<TextView>(R.id.signUp_link)
        tvSignUp.setOnClickListener {
            val bottomSheetDialogFragment = SignUp()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }

        // Set click listener for login button
        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val adminEmail = "Admin@gmail.com"
            val adminPassword = "Admin123"
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email == adminEmail && password == adminPassword) {
                Toast.makeText(this, "Admin Login successful", Toast.LENGTH_SHORT).show()
                val mainIntent = Intent(this, AdminDashboard::class.java)
                startActivity(mainIntent)
            }


            val cursor = db.rawQuery("SELECT * FROM USERS WHERE EMAIL = ?", arrayOf(email))
            if (cursor.moveToFirst()) {
                val email1 = cursor.getString(2)
                val password1 = cursor.getString(3)

                if (password == password1) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val mainIntent = Intent(this, HomePage::class.java)
                    startActivity(mainIntent)
                    //finish()
                } else {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
        }
    }
}

