package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class userAddCustomItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add_custom_item)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        //Catergory Spinner
        val types = resources.getStringArray(R.array.Types)
        val catespinner= findViewById<Spinner>(R.id.catspinner)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item , types)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_category_layout)
        catespinner.adapter = arrayAdapter

        //Brand Spinner
        val brands = resources.getStringArray(R.array.Brands)
        val bradspinner= findViewById<Spinner>(R.id.bradspinner)
        val arrayAdapter2 = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item , brands)
        arrayAdapter2.setDropDownViewResource(R.layout.brand_spinner)
        bradspinner.adapter = arrayAdapter2

        val customAdd = findViewById<Button>(R.id.btnadd)
        customAdd.setOnClickListener {
            val intent = Intent(this, User_addItems::class.java)
            startActivity(intent)
        }
        val button3 = findViewById<Button>(R.id.btnlist)
        button3.setOnClickListener {
            val intent = Intent(this, ListView::class.java)
            startActivity(intent)
        }


        val buttonList = findViewById<Button>(R.id.btnlist)
        buttonList.setOnClickListener {
            val intent = Intent(this@userAddCustomItem, ListView::class.java)
            startActivity(intent)
        }

    }
}