package com.example.electricitysaver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.util.Locale.Category

class User_addItems : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add_items)

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

    }
}
