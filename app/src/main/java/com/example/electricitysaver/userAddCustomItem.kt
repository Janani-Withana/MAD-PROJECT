package com.example.electricitysaver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class userAddCustomItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add_custom_item)
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

        val button6 = findViewById<Button>(R.id.btnadd)
        button6.setOnClickListener {
            val intent = Intent(this, User_addItems::class.java)
            startActivity(intent)
        }
        val button5 = findViewById<Button>(R.id.btnlist)
        button5.setOnClickListener {
            val intent = Intent(this, ListView::class.java)
            startActivity(intent)
        }



    }
}