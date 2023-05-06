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
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class SignUp : AppCompatActivity() {
    private lateinit var edtName : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPwd : EditText
    private lateinit var re_enter_pwd : EditText
    private lateinit var btnSignUp : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_bottomsheet_layout)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.parseColor("#133B5C") // Replace with your desired color
        }

        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPwd = findViewById(R.id.edtPwd)
        re_enter_pwd = findViewById(R.id.re_enter_pwd)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener(){
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (edtName.text.toString().isEmpty()) {
                edtName.error = "Name cannot be empty"
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if (!edtEmail.text.toString().matches(emailPattern.toRegex())) {
                edtEmail.error = "Invalid email address"
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                edtEmail.setText("")
            }
            else if (edtPwd.text.toString().length < 8) {
                edtPwd.error = "Password must be at least 8 characters long"
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()

            }
            else if (re_enter_pwd.text.toString() != edtPwd.text.toString()) {
                re_enter_pwd.error = "Passwords do not match"
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }else{
                var helper = UserItemDbHelper(applicationContext)
                var db = helper.readableDatabase

                var cv = ContentValues().apply {
                    put("USERNAME", edtName.text.toString())
                    put("EMAIL", edtEmail.text.toString())
                    put("PASSWORD", edtPwd.text.toString())
                }

                val rowsAffected = db.insert("USERS", null, cv)
                if (rowsAffected > 0) {
                    var ad = AlertDialog.Builder(this)
                    ad.setTitle("Add record")
                    ad.setMessage("Record Inserted Successfully....!")
                    ad.setPositiveButton("OK", DialogInterface.OnClickListener{ DialogInterface, i ->
                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)
                    })
                    ad.show()
                }else{
                    Toast.makeText(this, "Insert Not successful", Toast.LENGTH_SHORT).show()
                }

            }
            val mainIntent = Intent(this, userAddCustomItem::class.java)
            startActivity(mainIntent)

        }


    }


}