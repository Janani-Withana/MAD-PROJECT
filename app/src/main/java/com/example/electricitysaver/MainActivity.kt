package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button5 = findViewById<Button>(R.id.tactivity)
        button5.setOnClickListener {
            val intent = Intent(this, User_addItems::class.java)
            startActivity(intent)
        }








        // Hide the status bar
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // If you want to make the action bar (title bar) disappear as well, add this code:
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.parseColor("#133B5C") // Replace with your desired color
        }
    }
}